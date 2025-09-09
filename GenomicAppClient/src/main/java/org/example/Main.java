package org.example;

import org.Networks_layers.TCPClient;
import org.UserFile.FastaWriter;
import org.UserFile.user;
import org.UserFile.PatientInputHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(new File("configuration.properties")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
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
            // Pedir datos al usuario por consola
            user user = PatientInputHandler.readFromConsole();

            // Crear el archivo FASTA dinámicamente
            String fastaPath = user.getDocumentId() + ".fasta";
            FastaWriter.writePatientFasta(
                    user.getPatientId() != null ? user.getPatientId() : "TEMP_ID",
                    user.getFullName(),
                    user.getDocumentId(),
                    user.getContactEmail(),
                    user.getRegistrationDate(),
                    user.getAge(),
                    user.getSex(),
                    user.getSequence(),
                    fastaPath
            );
            System.out.println("✅ Archivo FASTA generado en: " + fastaPath);

            // Enviar archivo al servidor
            TCPClient client = new TCPClient("169.254.74.251", 2020);
            client.sendFastaFile(new File(fastaPath));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
