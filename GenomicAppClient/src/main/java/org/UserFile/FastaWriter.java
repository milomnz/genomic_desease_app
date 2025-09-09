package org.UserFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class FastaWriter {

    public static void writePatientFasta(String patientId,
                                         String fullName,
                                         String documentId,
                                         String contactEmail,
                                         LocalDate registrationDate,
                                         int age,
                                         String sex,
                                         String sequence,
                                         String filePath) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Línea de encabezado con metadata
            writer.write(">" + patientId +
                    "|full_name=" + fullName +
                    "|document_id=" + documentId +
                    "|email=" + contactEmail +
                    "|registration_date=" + registrationDate +
                    "|age=" + age +
                    "|sex=" + sex);
            writer.newLine();

            // Escribir la secuencia en bloques de 60 caracteres (estándar en FASTA)
            int lineLength = 60;
            for (int i = 0; i < sequence.length(); i += lineLength) {
                int end = Math.min(i + lineLength, sequence.length());
                writer.write(sequence.substring(i, end));
                writer.newLine();
            }
        }
    }
}
