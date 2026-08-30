package com.dental.clinic.service;

import com.dental.clinic.model.Appointment;
import com.dental.clinic.model.Dentist;
import com.dental.clinic.model.TreatmentType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentService {

    // Register new appointment
    Appointment registerAppointment(String nic, String patientName, String address, String contactNumber,
                                    int dentistId, List<Integer> treatmentIds, LocalDate date, LocalTime time);

    // Search appointment by number
    Appointment searchAppointment(String appointmentNumber);

    // All appointments
    List<Appointment> getAllAppointments();

    // Update appointment status
    void updateAppointmentStatus (String appointmentNumber, String status);

    // Update appointment date, time, status and treatments
    void updateAppointment(String appointmentNumber, LocalDate date, LocalTime time, String status, List<Integer> treatmentIds);

    // All dentists
    List<Dentist> getAllDentists ();

    // All treatment type
    List<TreatmentType> getAllTreatmentTypes();

}
