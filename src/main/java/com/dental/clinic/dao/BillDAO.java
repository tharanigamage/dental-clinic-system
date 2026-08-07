package com.dental.clinic.dao;

import com.dental.clinic.model.Bill;
import java.util.List;

public interface BillDAO {

    void save (Bill bill);

    Bill findByAppointmentNumber (String appointmentNumber);

    int countAll();

    List <Bill> findAll();
}
