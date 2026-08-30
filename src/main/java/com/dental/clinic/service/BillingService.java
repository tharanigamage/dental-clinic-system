package com.dental.clinic.service;

import com.dental.clinic.model.Bill;
import java.util.List;

public interface BillingService {

    // Generate bill
    Bill generateBill(String appointmentNumber);

    // All bills
    List <Bill> getAllBills();

}
