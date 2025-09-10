package org.example;

import org.Networks_layers.TCPClient;
import org.UserFile.FastaReader;
import org.UserFile.PatientInputHandler;
import org.UserFile.user;
import org.Utils.ChecksumUtil;

import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(new File("configuration.properties")));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        String certificateRoute = p.getProperty("SSL_CERTIFICATE_ROUTE");
        String certificatePassword = p.getProperty("SSL_PASSWORD");

        System.setProperty("javax.net.ssl.keyStore", certificateRoute);
        System.setProperty("javax.net.ssl.keyStorePassword", certificatePassword);
        System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
        System.setProperty("javax.net.ssl.trustStore", certificateRoute);
        System.setProperty("javax.net.ssl.trustStorePassword", certificatePassword);
        System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");

        try {
            user user = PatientInputHandler.readFromConsole();
            System.out.println("DEBUG -> user capturado: " + user);

            String fastaPath = FastaReader.askAndReadFastaPath();
            File fastaFile = new File(fastaPath);

            String fastaContent = Files.readString(fastaFile.toPath())
                    .replace("\n", "")
                    .replace("\r", "");
            user.setSequence(fastaContent);

            String checksum = ChecksumUtil.calculateMD5(fastaFile);
            user.setChecksumFasta(checksum);

            user.setFileSizeBytes(fastaFile.length());

            String message = String.join(";",
                    safe(user.getFullName()),
                    safe(user.getDocumentId()),
                    String.valueOf(user.getAge()),
                    safe(user.getContactEmail()),
                    safe(user.getSex()),
                    safe(user.getClinicalNotes()),
                    safe(user.getSequence()),
                    user.getChecksumFasta(),
                    String.valueOf(user.getFileSizeBytes())
            );

            System.out.println("\nEnviando al servidor:\n" + message + "\n");

            TCPClient client = new TCPClient("169.254.74.251", 2020);
            client.sendMessage(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String safe(String value) {
        return value != null ? value : "";
    }
}
