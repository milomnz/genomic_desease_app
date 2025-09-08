package Networks_layers;

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
                String[] parts = message.split(":");
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
