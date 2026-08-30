package com.dental.clinic.dao;

import com.dental.clinic.model.Appointment;
import com.dental.clinic.model.Bill;

import java.time.LocalDate;
import java.util.List;

public interface ReportDAO {

    // Find appointments by date
    List<Appointment> findAppointmentsByDate(LocalDate date);

    // Find bills by month
    List<Bill> findBillsByMonth(int month, int year);

    // Find appointments bt dentist and date range
    List<Appointment> findAppointmentsByDentistAndDateRange(int dentistId, LocalDate from, LocalDate to);
}
