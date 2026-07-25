package com.dental.clinic.model;

public class Dentist {
    private int dentistId;
    private String name;
    private String specialization;
    private double consultationFee;

    public Dentist () {
    }
    public Dentist (int dentistId, String name, String specialization, double consultationFee){
        this.dentistId= dentistId;
        this.name = name;
        this.specialization = specialization;
        this.consultationFee = consultationFee;
    }
    public int getDentistId(){
        return dentistId;
    }
    public void setDentistId (int dentistId){
        this.dentistId = dentistId;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getSpecialization(){
        return specialization;
    }
    public void setSpecialization (String specialization){
        this.specialization = specialization;
    }
    public double getConsultationFee(){
        return consultationFee;
    }
    public void setConsultationFee(double consultationFee){
        this.consultationFee = consultationFee;
    }

    @Override
    public String toString(){
        return "Dentist { " +
                "dentistId = " +  dentistId +
                ", name =' "  + name + '\'' +
                ", specialization =' " + specialization + '\'' +
                ", consultationFee =' " + consultationFee +
                '}';
    }
}
