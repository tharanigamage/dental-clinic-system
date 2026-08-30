package com.dental.clinic.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Appointment {
    // Appointment details
    private String appointmentNumber;
    private Patient patient;
    private Dentist dentist;
    private List<TreatmentType> treatmentTypes;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status;

    //Default Constructor
    public Appointment() {
    }

    //Constructor
    public Appointment(String appointmentNumber, Patient patient, Dentist dentist, List<TreatmentType> treatmentTypes, LocalTime appointmentTime, LocalDate appointmentDate, String status) {
        this.appointmentNumber = appointmentNumber;
        this.patient = patient;
        this.dentist = dentist;
        this.treatmentTypes = treatmentTypes;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    // Getters and setters
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

    public List<TreatmentType> getTreatmentTypes() {
        return treatmentTypes;
    }

    public void setTreatmentTypes(List<TreatmentType> treatmentTypes) {
        this.treatmentTypes = treatmentTypes;
    }

    public double getTotalTreatmentCost() {
        double total = 0;
        if (treatmentTypes != null) {
            for (TreatmentType t : treatmentTypes) {
                total += t.getCost();
            }
        }
        return total;
    }

    public String getTreatmentNamesDisplay() {
        if (treatmentTypes == null || treatmentTypes.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < treatmentTypes.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(treatmentTypes.get(i).getTreatmentName());
        }
        return sb.toString();
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

    // Return appointment details as text
    @Override
    public String toString() {
        return "Appointment { " +
                "appointmentNumber = " + appointmentNumber + '\'' +
                ", patient =' " + patient +
                ", dentist =' " + dentist +
                ", treatmentTypes =' " + treatmentTypes +
                ", appointmentDate =' " + appointmentDate +
                ", appointmentTime =' " + appointmentTime +
                ", status =' " + status + '\'' +
                '}';
    }
}
