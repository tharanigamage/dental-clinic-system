package com.dental.clinic.dao;

import com.dental.clinic.model.Appointment;
import com.dental.clinic.model.Bill;

import java.time.LocalDate;
import java.util.List;

public interface ReportDAO {

    List<Appointment> findAppointmentsByDate(LocalDate date);
    List<Bill> findBillsByMonth(int month, int year);
    List<Appointment> findAppointmentsByDentistAndDateRange(int dentistId, LocalDate from, LocalDate to);
}
