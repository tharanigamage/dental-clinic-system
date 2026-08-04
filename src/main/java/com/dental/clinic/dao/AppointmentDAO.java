package com.dental.clinic.dao;

import com.dental.clinic.model.Appointment;
import java.util.List;
import java.time.LocalTime;
import java.time.LocalDate;

public interface AppointmentDAO {

    void save (Appointment appointment);

    Appointment findByAppointmentNumber (String appointmentNumber);

    List<Appointment> findAll();

    void updateStatus (String appointmentNumber, String status);

    int countAll();

    boolean existsByDentistDateTime (int dentistId, LocalDate date, LocalTime time);
}
