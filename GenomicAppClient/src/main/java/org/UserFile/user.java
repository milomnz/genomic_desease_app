package org.UserFile;

import java.time.LocalDate;

public class user {
    private String patientId;
    private String fullName;
    private String documentId;
    private String contactEmail;
    private LocalDate registrationDate;
    private int age;
    private String sex;
    private String clinicalNotes;
    private String sequence;
    private String checksumFasta;
    private long fileSizeBytes;

    public user(String fullName, String documentId, String contactEmail,
                LocalDate registrationDate, int age, String sex, String clinicalNotes) {
        this.fullName = fullName;
        this.documentId = documentId;
        this.contactEmail = contactEmail;
        this.registrationDate = registrationDate;
        this.age = age;
        this.sex = sex;
        this.clinicalNotes = clinicalNotes;
    }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getFullName() { return fullName; }
    public String getDocumentId() { return documentId; }
    public String getContactEmail() { return contactEmail; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public int getAge() { return age; }
    public String getSex() { return sex; }
    public String getClinicalNotes() { return clinicalNotes; }
    public String getSequence() { return sequence; }

    public String getChecksumFasta() { return checksumFasta; }
    public void setChecksumFasta(String checksumFasta) { this.checksumFasta = checksumFasta; }

    public long getFileSizeBytes() { return fileSizeBytes; }
    public void setFileSizeBytes(long fileSizeBytes) { this.fileSizeBytes = fileSizeBytes; }


    public void setSequence(String sequence) { this.sequence = sequence; }

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
                ", clinicalNotes='" + clinicalNotes + '\'' +
                ", sequence='" + sequence + '\'' +
                '}';
    }
}
