package com.dental.clinic.dao;

import com.dental.clinic.model.Bill;
import java.util.List;

public interface BillDAO {

    // Save bill
    void save (Bill bill);

    // Find by appointment number
    Bill findByAppointmentNumber (String appointmentNumber);

    // Total bills count
    int countAll();

    //All bills
    List <Bill> findAll();
}
