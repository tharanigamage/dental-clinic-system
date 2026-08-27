package com.dental.clinic.service;

import com.dental.clinic.model.Appointment;
import com.dental.clinic.model.Dentist;
import com.dental.clinic.model.TreatmentType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentService {

    Appointment registerAppointment(String patientName, String address, String contactNumber,
                                    int dentistId, int treatmentId, LocalDate date, LocalTime time);

    Appointment searchAppointment(String appointmentNumber);

    List<Appointment> getAllAppointments();

    void updateAppointmentStatus (String appointmentNumber, String status);

    void updateAppointment(String appointmentNumber, LocalDate date, LocalTime time, String status);

    List<Dentist> getAllDentists ();

    List<TreatmentType> getAllTreatmentTypes();

}
