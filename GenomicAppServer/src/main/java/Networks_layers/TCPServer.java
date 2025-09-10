package Networks_layers;

import desease.DiseaseService;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TCPServer {
    private int serverPort;

    public TCPServer(int serverPort) {
        this.serverPort = serverPort;
    }

    public void start() {
        try {
            SSLServerSocketFactory sslSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket serverSocket = (SSLServerSocket) sslSocketFactory.createServerSocket(serverPort);
            System.out.println("Server started on port: " + serverPort);
            while (true) {
                // Espera conexiones
                SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
                System.out.println("Cliente conectado...");

                // Manejo en un hilo separado
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    private void handleClient(SSLSocket clientSocket) {
        try (
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())
        ) {
            String message = dis.readUTF();
            System.out.println("Received message: " + message);

            // Separar por pipes
            String[] parts = message.split(";");
            if (parts.length < 8) {
                out.writeUTF("Error: formato de mensaje inválido");
                return;
            }

            // Parsear datos
            int document = Integer.parseInt(parts[2]);
            long age = Long.parseLong(parts[5]);
            char gender = parts[6].charAt(0);

            DiseaseService diseaseService = new DiseaseService(
                    parts[0],  // tempId
                    parts[1],  // fullName
                    document,
                    parts[3],  // email
                    parts[4],  // registerDate
                    age,
                    gender,
                    parts[7]   // geneticCode
            );

            // Ejecutar el análisis en un hilo aparte
            new Thread(diseaseService).start();

            String response = " Paciente recibido: " + parts[1] + " (ID: " + diseaseService.getId() + ")";
            out.writeUTF(response);

        } catch (IOException e) {
            System.out.println("Error manejando cliente: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ignored) {}
        }
    }
}
