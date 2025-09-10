package org.UserFile;

import java.time.LocalDate;
import java.util.Scanner;

public class PatientInputHandler {

    public static user readFromConsole() {
        Scanner scanner = new Scanner(System.in);


        String fullName;
        while (true) {
            System.out.print("Ingrese el nombre completo: ");
            fullName = scanner.nextLine().trim();
            if (fullName.matches("^[a-zA-ZÁÉÍÓÚÑáéíóúñ ]+$")) break;
            System.out.println("Nombre inválido. Solo letras y espacios permitidos.");
        }


        String documentId;
        while (true) {
            System.out.print("Ingrese el documento: ");
            documentId = scanner.nextLine().trim();
            if (documentId.matches("^[0-9]+$")) break;
            System.out.println("Documento inválido. Solo números permitidos.");
        }


        String contactEmail;
        while (true) {
            System.out.print("Ingrese el email de contacto: ");
            contactEmail = scanner.nextLine().trim();
            if (contactEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) break;
            System.out.println("Email inválido.");
        }


        int age;
        while (true) {
            System.out.print("Ingrese la edad: ");
            String ageInput = scanner.nextLine().trim();
            if (ageInput.matches("^[0-9]{1,3}$")) {
                age = Integer.parseInt(ageInput);
                break;
            }
            System.out.println("Edad inválida. Debe ser un número entre 0 y 999.");
        }


        String sex;
        while (true) {
            System.out.print("Ingrese el sexo (M/F): ");
            sex = scanner.nextLine().trim().toUpperCase();
            if (sex.equals("M") || sex.equals("F")) break;
            System.out.println("Sexo inválido. Solo 'M' o 'F'.");
        }


        System.out.print("Ingrese notas clínicas (opcional): ");
        String clinicalNotes = scanner.nextLine().trim();
        if (!clinicalNotes.matches("^[a-zA-Z0-9 áéíóúÁÉÍÓÚñÑ.,;:-]*$")) {
            System.out.println("Notas clínicas contenían caracteres inválidos, se limpiaron.");
            clinicalNotes = clinicalNotes.replaceAll("[^a-zA-Z0-9 áéíóúÁÉÍÓÚñÑ.,;:-]", "");
        }

        return new user(fullName, documentId, contactEmail,
                LocalDate.now(), age, sex, clinicalNotes);
    }
}
