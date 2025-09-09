package org.Networks_layers;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;

public class TCPClient {
    private final String serverAddress;
    private final int serverPort;
    private SSLSocket clientSocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public TCPClient(String serverAddress, int serverPort){
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void connect() throws IOException {
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        this.clientSocket = (SSLSocket) sslSocketFactory.createSocket(serverAddress, serverPort);
        System.out.println("Connected to server: " + this.serverAddress + ":" + this.serverPort);
        this.dataInputStream = new DataInputStream(this.clientSocket.getInputStream());
        this.dataOutputStream = new DataOutputStream(this.clientSocket.getOutputStream());
    }

    public void sendFastaFile(File fastaFile) {
        try {
            this.connect();

            // Paso 1: enviar comando
            this.dataOutputStream.writeUTF("CREATE_PATIENT");

            // Paso 2: enviar nombre de archivo y tamaño
            this.dataOutputStream.writeUTF(fastaFile.getName());
            this.dataOutputStream.writeLong(fastaFile.length());

            // Paso 3: enviar contenido
            try (FileInputStream fis = new FileInputStream(fastaFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    this.dataOutputStream.write(buffer, 0, bytesRead);
                }
            }
            this.dataOutputStream.flush();

            // Paso 4: leer respuesta
            String response = this.dataInputStream.readUTF();
            System.out.println("Received response: " + response);

        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        } finally {
            this.closeConnection();
        }
    }

    public void closeConnection() {
        try {
            if (this.dataInputStream != null) this.dataInputStream.close();
            if (this.dataOutputStream != null) this.dataOutputStream.close();
            if (this.clientSocket != null) this.clientSocket.close();
        } catch (IOException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
