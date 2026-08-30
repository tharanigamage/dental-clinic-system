package com.dental.clinic.model;

public class Patient {
    // Patient details
    private int patientId;
    private String nic;
    private String name;
    private String address;
    private String contactNumber;

    //Default Constructor
    public Patient() {
    }

    // Constructor
    public Patient(int patientId, String nic, String name, String address, String contactNumber) {
        this.patientId = patientId;
        this.nic = nic;
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    // Getters and setters
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    // Return patient details as text
    @Override
    public String toString() {
        return "Patient{" +
                "patientId=" + patientId +
                ", nic='" + nic + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }
}