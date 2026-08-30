package com.dental.clinic.service;

import com.dental.clinic.model.Appointment;
import com.dental.clinic.model.Bill;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    // get appointments selected date
    List<Appointment> getDailyAppointments(LocalDate date);

    // Get monthly revenue selected month and year
    List<Bill> getMonthlyRevenue(int month, int year);

    // Get dentist appointments selected date range
    List<Appointment> getDentistAppointments(int dentistId, LocalDate from, LocalDate to);

}
