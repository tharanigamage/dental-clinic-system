package com.dental.clinic.service;

import com.dental.clinic.dao.*;
import com.dental.clinic.model.Appointment;
import com.dental.clinic.model.Dentist;
import com.dental.clinic.model.Patient;
import com.dental.clinic.model.TreatmentType;
import com.dental.clinic.util.ValidationUtil;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;

public class AppointmentServiceImpl implements AppointmentService{

    private final PatientDAO patientDAO;
    private final DentistDAO dentistDAO;
    private final TreatmentTypeDAO treatmentTypeDAO;
    private final AppointmentDAO appointmentDAO;

    public AppointmentServiceImpl(){
        this.patientDAO = new PatientDAOImpl();
        this.dentistDAO = new DentistDAOImpl();
        this.treatmentTypeDAO = new TreatmentTypeDAOImpl();
        this.appointmentDAO = new AppointmentDAOImpl();

    }

    public AppointmentServiceImpl(PatientDAO patientDAO, DentistDAO dentistDAO, TreatmentTypeDAO treatmentTypeDAO, AppointmentDAO appointmentDAO){
        this.patientDAO = patientDAO;
        this.dentistDAO = dentistDAO;
        this.treatmentTypeDAO = treatmentTypeDAO;
        this.appointmentDAO = appointmentDAO;
    }

    @Override
    public Appointment registerAppointment (String patientName, String address, String contactNumber,
                                            int dentistId, int treatmentId, LocalDate date, LocalTime time){

        if (!ValidationUtil.isValidName(patientName)){
            throw new IllegalArgumentException("Please enter a valid patient name (letters only, at least 2 characters).");
        }

        if (ValidationUtil.isNullorBlank(address)){
            throw new IllegalArgumentException("Address cannot be empty.");
        }

        if (!ValidationUtil.isValidContactNumber(contactNumber)){
            throw new IllegalArgumentException("Contact number must be exactly 10 digits.");
        }

        if (!ValidationUtil.isPositiveId(dentistId)){
            throw new IllegalArgumentException("Please select a dentist.");
        }

        if (!ValidationUtil.isPositiveId(treatmentId)){
            throw new IllegalArgumentException("Please select a treatment type.");
        }

        if (date == null || time == null){
            throw new IllegalArgumentException("Please enter a valid appointment date and time.");
        }

        if (!ValidationUtil.isFutureOrTodayDate(date)){
            throw new IllegalArgumentException("Appointment date cannot be in the past.");
        }

        if (appointmentDAO.existsByDentistDateTime(dentistId,date,time)){
            throw new IllegalArgumentException("This dentist already has an appointment at that date and time. Please choose a different slot.");
        }

        Patient patient = new Patient();
        patient.setName(patientName.trim());
        patient.setAddress(address.trim());
        patient.setContactNumber(contactNumber.trim());
        int patientId = patientDAO.save(patient);
        patient.setPatientId(patientId);

        Dentist dentist = dentistDAO.findById(dentistId);
        TreatmentType treatmentType = treatmentTypeDAO.findById(treatmentId);

        String appointmentNumber = generateAppointmentNumber();

        Appointment appointment = new Appointment();
        appointment.setAppointmentNumber(appointmentNumber);
        appointment.setPatient(patient);
        appointment.setDentist(dentist);
        appointment.setTreatmentType(treatmentType);
        appointment.setAppointmentDate(date);
        appointment.setAppointmentTime(time);
        appointment.setStatus("Pending");

        appointmentDAO.save(appointment);

        return appointment;
    }

    @Override
    public Appointment searchAppointment (String appointmentNumber){
        return appointmentDAO.findByAppointmentNumber(appointmentNumber);
    }

    @Override
    public List<Appointment> getAllAppointments(){
        return appointmentDAO.findAll();
    }

    @Override
    public void updateAppointmentStatus (String appointmentNumber, String status){
        appointmentDAO.updateStatus(appointmentNumber,status);
    }

    @Override
    public List<Dentist> getAllDentists(){
        return dentistDAO.findAll();
    }

    @Override
    public List<TreatmentType> getAllTreatmentTypes(){
        return treatmentTypeDAO.findAll();
    }

    private String generateAppointmentNumber(){
        int nextNumber = appointmentDAO.countAll() + 1;
        return String.format("APT%03d", nextNumber);
    }
}
