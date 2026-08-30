package com.dental.clinic.dao;

import com.dental.clinic.model.Appointment;
import java.util.List;
import java.time.LocalTime;
import java.time.LocalDate;

public interface AppointmentDAO {

    // Save new appointment
    void save (Appointment appointment);

    // Find by appointment number
    Appointment findByAppointmentNumber (String appointmentNumber);

    // All appointments
    List<Appointment> findAll();

    // Update the status
    void updateStatus (String appointmentNumber, String status);

    // Update appointment details
    void updateAppointment(String appointmentNumber, LocalDate date, LocalTime time, String status, String treatmentIdsCsv);

    // Total appointments count
    int countAll();

    // Check dentist already has an appointment at the given date and time
    boolean existsByDentistDateTime (int dentistId, LocalDate date, LocalTime time);
}
