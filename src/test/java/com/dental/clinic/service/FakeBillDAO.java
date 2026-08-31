package com.dental.clinic.service;

import com.dental.clinic.dao.BillDAO;
import com.dental.clinic.model.Bill;

import java.util.ArrayList;
import java.util.List;

public class FakeBillDAO implements BillDAO {

    private final List<Bill> bills = new ArrayList<>();

    @Override
    public void save(Bill bill) {
        bills.add(bill);
    }

    @Override
    public Bill findByAppointmentNumber(String appointmentNumber) {
        for (Bill b : bills) {
            if (b.getAppointment().getAppointmentNumber().equals(appointmentNumber)) return b;
        }
        return null;
    }

    @Override
    public int countAll() {
        return bills.size();
    }

    @Override
    public List<Bill> findAll() {
        return bills;
    }
}