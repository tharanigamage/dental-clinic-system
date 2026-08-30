package com.dental.clinic.model;

import java.time.LocalDate;

public class Bill {
    // Bill details
    private String billId;
    private Appointment appointment;
    private double consultationFee;
    private double treatmentCost;
    private double totalAmount;
    private LocalDate billDate;

    //Default Constructor
    public Bill() {
    }

    // Constructor
    public Bill(String billId, Appointment appointment, double consultationFee, double treatmentCost, double totalAmount, LocalDate billDate) {
        this.billId = billId;
        this.appointment = appointment;
        this.consultationFee = consultationFee;
        this.treatmentCost = treatmentCost;
        this.totalAmount = totalAmount;
        this.billDate = billDate;
    }

    // Getters and setters
    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public double getTreatmentCost() {
        return treatmentCost;
    }

    public void setTreatmentCost(double treatmentCost) {
        this.treatmentCost = treatmentCost;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    // Return bill details as text
    @Override
    public String toString() {
        return "Bill { " +
                "billId = " + billId + '\'' +
                ", appointment =' " + appointment.getAppointmentNumber() +
                ", consultationFee =' " + consultationFee +
                ", treatmentCost =' " + treatmentCost +
                ", totalAmount =' " + totalAmount +
                ", billDate =' " + billDate +
                '}';
    }
}
