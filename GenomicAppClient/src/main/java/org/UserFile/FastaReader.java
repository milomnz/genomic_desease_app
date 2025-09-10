package org.UserFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class FastaReader {

    public static String askAndReadFastaPath() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la ruta del archivo FASTA: ");
        return scanner.nextLine().trim();
    }

    public static String readContent(File fastaFile) throws IOException {
        if (!fastaFile.exists()) {
            throw new IOException("El archivo FASTA no existe: " + fastaFile.getAbsolutePath());
        }


        String content = Files.readString(fastaFile.toPath())
                .replace("\r", "")
                .trim();

        String[] lines = content.split("\n");

        if (lines.length < 2) {
            throw new IOException("Formato FASTA inválido: faltan líneas");
        }


        String header = lines[0].trim();
        if (header.startsWith(">")) {
            header = header.substring(1).trim();
        }


        StringBuilder sequence = new StringBuilder();
        for (int i = 1; i < lines.length; i++) {
            sequence.append(lines[i].trim());
        }
        System.out.print(header);
        System.out.print(sequence);
        // ✅ devolver header y secuencia separados por ";"
        return header + ";" + sequence.toString();
    }
}
