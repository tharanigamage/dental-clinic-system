package com.dental.clinic.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private String appointmentNumber;
    private Patient patient;
    private Dentist dentist;
    private TreatmentType treatmentType;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status;

    //default constructor
    public Appointment() {
    }

    //constructor
    public Appointment(String appointmentNumber, Patient patient, Dentist dentist, TreatmentType treatmentType, LocalTime appointmentTime, LocalDate appointmentDate, String status) {
        this.appointmentNumber = appointmentNumber;
        this.patient = patient;
        this.dentist = dentist;
        this.treatmentType = treatmentType;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Dentist getDentist() {
        return dentist;
    }

    public void setDentist(Dentist dentist) {
        this.dentist = dentist;
    }

    public TreatmentType getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(TreatmentType treatmentType) {
        this.treatmentType = treatmentType;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Appointment { " +
                "appointmentNumber = " + appointmentNumber + '\'' +
                ", patient =' " + patient +
                ", dentist =' " + dentist +
                ", treatmentType =' " + treatmentType +
                ", appointmentDate =' " + appointmentDate +
                ", appointmentTime =' " + appointmentTime +
                ", status =' " + status + '\'' +
                '}';
    }
}
