package Networks_layers;

import desease.DiseaseService;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPServer {
    private int serverPort;
    private DiseaseService DiseaseService;

    public TCPServer(int serverPort){
        this.serverPort = serverPort;
    }


    public void start(){
        try{
            SSLServerSocketFactory sslSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            ServerSocket serverSocket = (SSLServerSocket)sslSocketFactory.createServerSocket(serverPort);
            System.out.println("Server started on port: " + serverPort);
            while(true){
                Socket clientSocket = serverSocket.accept();
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                String message = dis.readUTF();
                String[] parts = message.split("|");
                int document = Integer.parseInt(parts[2]);
                long age = Long.parseLong(parts[5]);
                char gender = parts[6].charAt(0);
                DiseaseService = new DiseaseService(parts[0], parts[1], document, parts[3], parts[4], age, gender, parts[7]);

                System.out.println("Received message: " + message);
                String response = "Name "+parts[0]+" Last Name "+parts[1];
                out.writeUTF(response);
                clientSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }

    }

}
