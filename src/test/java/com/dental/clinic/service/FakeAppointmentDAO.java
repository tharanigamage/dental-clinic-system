package com.dental.clinic.service;

import com.dental.clinic.dao.AppointmentDAO;
import com.dental.clinic.model.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FakeAppointmentDAO implements AppointmentDAO {

    private final List<Appointment> appointments = new ArrayList<>();

    @Override
    public void save(Appointment appointment) {
        appointments.add(appointment);
    }

    @Override
    public Appointment findByAppointmentNumber(String appointmentNumber) {
        for (Appointment a : appointments) {
            if (a.getAppointmentNumber().equals(appointmentNumber)) return a;
        }
        return null;
    }

    @Override
    public List<Appointment> findAll() {
        return appointments;
    }

    @Override
    public void updateStatus(String appointmentNumber, String status) {
        Appointment a = findByAppointmentNumber(appointmentNumber);
        if (a != null) a.setStatus(status);
    }

    @Override
    public void updateAppointment(String appointmentNumber, LocalDate date, LocalTime time, String status, String treatmentIdsCsv) {
        Appointment a = findByAppointmentNumber(appointmentNumber);
        if (a != null) {
            a.setAppointmentDate(date);
            a.setAppointmentTime(time);
            a.setStatus(status);
        }
    }

    @Override
    public int countAll() {
        return appointments.size();
    }

    @Override
    public boolean existsByDentistDateTime(int dentistId, LocalDate date, LocalTime time) {
        for (Appointment a : appointments) {
            if (a.getDentist().getDentistId() == dentistId
                    && a.getAppointmentDate().equals(date)
                    && a.getAppointmentTime().equals(time)
                    && !"Cancelled".equals(a.getStatus())) {
                return true;
            }
        }
        return false;
    }
}