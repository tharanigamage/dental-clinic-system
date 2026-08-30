package com.dental.clinic.model;

public class TreatmentType {
    // Treatment type details
    private int treatmentId;
    private String treatmentName;
    private double cost;

    //Default Constructor
    public TreatmentType() {
    }

    // Constructor
    public TreatmentType(int treatmentId, String treatmentName, double cost) {
        this.treatmentId = treatmentId;
        this.treatmentName = treatmentName;
        this.cost = cost;
    }

    // Getters and setters
    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    // Return treatment type details as text
    @Override
    public String toString() {
        return "TreatmentType { " +
                "treatmentId = " + treatmentId +
                ", treatmentName =' " + treatmentName + '\'' +
                ", cost =' " + cost +
                '}';
    }
}
