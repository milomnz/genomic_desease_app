package desease;

import org.jetbrains.annotations.Nullable;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

/**
 * @autor: milomnz
 * Service for founding diseases in genetic code
 */
public class DiseaseService implements Runnable {

    private String id;
    private String fullName;
    private final int document;
    private String email;
    private final String registerDate;
    private long age;
    private char gender;
    private String geneticCode;
    private static final String DATABASE_PATH = "C:\\Users\\jusha\\Documents\\backend\\genomicApp\\GenomicAppServer\\sickness_db";

    // Lista para almacenar las enfermedades encontradas
    private List<String> foundDiseases;

    public DiseaseService(String tempId, String fullName, int document, String email, String registerDate,
                          long age, char gender, String geneticCode) {
        this.id = generateId(tempId, document);
        this.fullName = fullName;
        this.document = document;
        this.email = email;
        this.registerDate = registerDate;
        this.age = age;
        this.gender = gender;
        this.geneticCode = geneticCode;
        this.foundDiseases = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            System.out.println("Analizando paciente: " + fullName + " (ID: " + id + ")");
            compareGeneticCode();
            showResults();
            exportResultsToCSV("C:\\Users\\jusha\\Documents\\backend\\genomicApp\\GenomicAppServer\\patients_db");
        } catch (IOException e) {
            System.err.println("Error durante el análisis: " + e.getMessage());
            throw new RuntimeException("Error en el servicio de detección de enfermedades", e);
        }
    }

    public String generateId(String tempId, int document) {
        return "P" + tempId + "_" + document;
    }

    /**
     * returns a array with two index.
     * 0. Header of disease file.
     * 1. Genetic code of disease
     */
    @Nullable
    private static String[] readDiseaseFasta(String folderPath, int index) throws IOException {
        String fileName = index + ".fasta";
        String filePath = folderPath + "/" + fileName;
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        if (lines.size() < 2) {
            System.err.println("Error: Archivo FASTA " + fileName + " inválido. No tiene encabezado y secuencia.");
            return null;
        }

        String header = lines.get(0);
        StringBuilder sequence = new StringBuilder();
        for (int i = 1; i < lines.size(); i++) {
            sequence.append(lines.get(i).trim());
        }

        return new String[]{header, sequence.toString()};
    }

    /**
     * Compara el código genético del paciente con la base de datos de enfermedades.
     */
    public void compareGeneticCode() throws IOException {
        String patientCode = geneticCode.toUpperCase().replaceAll("\\s+", "");
        System.out.println("Comparando código genético con base de datos...");

        for (int i = 1; i <= 8; i++) {
            try {
                String[] diseaseData = readDiseaseFasta(DATABASE_PATH, i);

                if (diseaseData != null) {
                    String header = diseaseData[0];
                    String diseaseSequence = diseaseData[1];
                    String diseaseName = extractDiseaseName(header);

                    if (diseaseName.isEmpty()) {
                        System.err.println("Error: No se pudo extraer el nombre de la enfermedad del archivo " + i + ".fasta");
                        continue;
                    }

                    System.out.println("Comparando con " + diseaseName + "...");

                    if (hasSequenceMatch(patientCode, diseaseSequence)) {
                        foundDiseases.add(diseaseName);
                        System.out.println("Coincidencia encontrada con: " + diseaseName);
                    } else {
                        System.out.println("Sin coincidencia con: " + diseaseName);
                    }
                }

            } catch (IOException e) {
                System.err.println("Error al procesar el archivo de enfermedad " + i + ": " + e.getMessage());
            }
        }
    }

    private String extractDiseaseName(String header) {
        String cleanHeader = header.startsWith(">") ? header.substring(1).trim() : header.trim();
        String[] parts = cleanHeader.split("\\s+|_"); // Divide por espacios o guiones bajos

        if (parts.length >= 2) {
            StringBuilder diseaseName = new StringBuilder();
            for (int i = 1; i < parts.length; i++) {
                if (i > 1) {
                    diseaseName.append(" ");
                }
                diseaseName.append(parts[i]);
            }
            return diseaseName.toString();
        }

        return cleanHeader;
    }

    /**
     * Busca si hay al menos una secuencia de 4 caracteres consecutivos
     * del código del paciente que esté presente en la secuencia de la enfermedad.
     */
    private boolean hasSequenceMatch(String patientCode, String diseaseSequence) {
        if (patientCode.length() < 4) {
            return false;
        }

        for (int i = 0; i <= patientCode.length() - 4; i++) {
            String substring = patientCode.substring(i, i + 4);
            if (diseaseSequence.contains(substring)) {
                return true;
            }
        }
        return false;
    }
    
    private void showResults() {
        System.out.println("RESULTADOS DEL ANÁLISIS");
        System.out.println("Paciente: " + fullName);
        System.out.println("ID: " + id);
        System.out.println("Documento: " + document);

        if (foundDiseases.isEmpty()) {
            System.out.println("\n No se encontraron coincidencias genéticas.");
        } else {
            System.out.println("\n Posibles coincidencias genéticas encontradas:");
            for (String disease : foundDiseases) {
                System.out.println("   • " + disease);
            }
        }
    }
    public void exportResultsToCSV(String outputPath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath, true))) {
            // Cabecera si el archivo está vacío
            File file = new File(outputPath);
            if (file.length() == 0) {
                writer.println("ID,FullName,Document,Email,RegisterDate,Age,Gender,FoundDiseases");
            }

            // Convertir lista de enfermedades a un solo string separado por ';'
            String diseases = foundDiseases.isEmpty() ? "None" : String.join(";", foundDiseases);

            // Escribir fila
            writer.printf("%s,%s,%d,%s,%s,%d,%c,%s%n",
                    id, fullName, document, email, registerDate, age, gender, diseases);

            System.out.println("Resultados exportados a: " + outputPath);

        } catch (IOException e) {
            System.err.println("Error exportando resultados a CSV: " + e.getMessage());
        }
    }


    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public List<String> getFoundDiseases() {
        return foundDiseases;
    }
}