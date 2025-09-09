package org.UserFile;

import java.time.LocalDate;
import java.util.Scanner;

public class PatientInputHandler {

    public static user readFromConsole() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el ID del paciente: ");
        String patientId = scanner.nextLine();

        System.out.print("Ingrese el nombre completo: ");
        String fullName = scanner.nextLine();

        System.out.print("Ingrese el documento: ");
        String documentId = scanner.nextLine();

        System.out.print("Ingrese el email de contacto: ");
        String contactEmail = scanner.nextLine();

        System.out.print("Ingrese la edad: ");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese el sexo (M/F): ");
        String sex = scanner.nextLine();

        System.out.print("Ingrese la secuencia genómica (A,C,G,T,N): ");
        String sequence = scanner.nextLine().toUpperCase();

        return new user(fullName, documentId, contactEmail,
                LocalDate.now(), age, sex, sequence);

    }
}
