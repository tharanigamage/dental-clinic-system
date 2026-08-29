package com.dental.clinic.service;

import com.dental.clinic.dao.*;
import com.dental.clinic.model.Appointment;
import com.dental.clinic.model.Dentist;
import com.dental.clinic.model.Patient;
import com.dental.clinic.model.TreatmentType;
import com.dental.clinic.util.ValidationUtil;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.ArrayList;
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
    public Appointment registerAppointment(String nic, String patientName, String address, String contactNumber,
                                           int dentistId, List<Integer> treatmentIds, LocalDate date, LocalTime time){

        if (ValidationUtil.isNullorBlank(nic)){
            throw new IllegalArgumentException("Please enter the patient's NIC.");
        }

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

        if (treatmentIds == null || treatmentIds.isEmpty()){
            throw new IllegalArgumentException("Please select at least one treatment type.");
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

        Patient patient = patientDAO.findByNic(nic.trim());
        if (patient == null) {
            patient = new Patient();
            patient.setNic(nic.trim());
            patient.setName(patientName.trim());
            patient.setAddress(address.trim());
            patient.setContactNumber(contactNumber.trim());
            int patientId = patientDAO.save(patient);
            patient.setPatientId(patientId);
        }

        Dentist dentist = dentistDAO.findById(dentistId);

        List<TreatmentType> treatmentTypes = new ArrayList<>();
        for (int treatmentId : treatmentIds) {
            TreatmentType t = treatmentTypeDAO.findById(treatmentId);
            if (t != null) {
                treatmentTypes.add(t);
            }
        }

        String appointmentNumber = generateAppointmentNumber();

        Appointment appointment = new Appointment();
        appointment.setAppointmentNumber(appointmentNumber);
        appointment.setPatient(patient);
        appointment.setDentist(dentist);
        appointment.setTreatmentTypes(treatmentTypes);
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
    public void updateAppointment(String appointmentNumber, LocalDate date, LocalTime time, String status) {

        if (date == null || time == null) {
            throw new IllegalArgumentException("Please enter a valid date and time.");
        }
        if (!ValidationUtil.isFutureOrTodayDate(date)) {
            throw new IllegalArgumentException("Appointment date cannot be in the past.");
        }
        if (!"Pending".equals(status) && !"Completed".equals(status) && !"Cancelled".equals(status)) {
            throw new IllegalArgumentException("Invalid status value.");
        }

        Appointment existing = appointmentDAO.findByAppointmentNumber(appointmentNumber);
        if (existing == null) {
            throw new IllegalArgumentException("Appointment not found: " + appointmentNumber);
        }


        boolean dateTimeChanged = !existing.getAppointmentDate().equals(date) || !existing.getAppointmentTime().equals(time);
        if (dateTimeChanged && appointmentDAO.existsByDentistDateTime(existing.getDentist().getDentistId(), date, time)) {
            throw new IllegalArgumentException("This dentist already has another appointment at that date and time.");
        }

        appointmentDAO.updateAppointment(appointmentNumber, date, time, status);
    }

    @Override
    public void updateAppointmentStatus(String appointmentNumber, String status) {
        appointmentDAO.updateStatus(appointmentNumber, status);
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
