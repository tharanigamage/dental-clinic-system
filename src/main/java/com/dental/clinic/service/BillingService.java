package com.dental.clinic.service;

import com.dental.clinic.model.Bill;
import java.util.List;

public interface BillingService {

    Bill generateBill(String appointmentNumber);

    List <Bill> getAllBills();

}
