package desease;

import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/*
* @autor: milomnz
*
*
* */

public class DiseaseService implements Runnable {
    private String id;
    private String fullName;
    private final int document;
    private String email;
    private final String  registerDate;
    private long age;
    private char gender;
    private String geneticCode;

    public DiseaseService(String tempId, String fullName, int document, String email, String registerDate, long age,  char gender, String geneticCode) {
        this.id = generateId(tempId,document);
        this.fullName = fullName;
        this.document = document;
        this.email = email;
        this.registerDate = registerDate;
        this.age = age;
        this.gender = gender;
        this.geneticCode = geneticCode;

    }

    @Override
    public void run() {
        /*
        try {


        }*catch (IOException e) {
            throw new RuntimeException(e);
        }
        */

    }

    public String generateId(String tempId, int document) {
        return "P"+tempId+"_"+document;
    }

    @Nullable
    public static String[] readDiseaseFasta(String folderPath, int index) throws IOException {
        String fileName = index + ".fasta";
        String filePath = folderPath + "/" + fileName;

        List<String> lines = Files.readAllLines(Paths.get(filePath));

        // A fasta file has at least two lines, header and sequence
        if (lines.size() >= 2) {
            String header = lines.get(0);
            String sequence = lines.get(1);
            String[] fastaFile = new String[2];
            fastaFile[0] = header;
            fastaFile[1] = sequence;
            return fastaFile;
        }else  {
            System.out.println("Error trying to reading fasta file in index: " + index);
            return null;
        }
    }


    public void compareGeneticCode() throws IOException {
        // We use 9 because we have 9 diseases. We also don't know how to use it with dynamic range. :(
        // milomnz..
        for (int i = 1; i < 9; i++) {
            String[] actualDesease = readDiseaseFasta("../../../../sickness_db", i);
            for (int j = 1; j < 9; j++) {
                System.out.println("Developing...");
            }
        }
    }
}
