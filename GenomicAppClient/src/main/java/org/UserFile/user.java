package org.UserFile;

import java.time.LocalDate;

public class user {
    private String patientId;           // lo asigna el servidor
    private String fullName;
    private String documentId;
    private String contactEmail;
    private LocalDate registrationDate;
    private int age;
    private String sex;
    private String sequence;            // la secuencia genómica

    // 🔹 Constructor sin patientId (porque lo genera el servidor)
    public user(String fullName, String documentId, String contactEmail,
                LocalDate registrationDate, int age, String sex, String sequence) {
        this.fullName = fullName;
        this.documentId = documentId;
        this.contactEmail = contactEmail;
        this.registrationDate = registrationDate;
        this.age = age;
        this.sex = sex;
        this.sequence = sequence;
    }

    // Getters y Setters
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getFullName() { return fullName; }
    public String getDocumentId() { return documentId; }
    public String getContactEmail() { return contactEmail; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public int getAge() { return age; }
    public String getSex() { return sex; }
    public String getSequence() { return sequence; }

    @Override
    public String toString() {
        return "User{" +
                "patientId='" + patientId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", documentId='" + documentId + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", registrationDate=" + registrationDate +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", sequence='" + sequence + '\'' +
                '}';
    }
}
