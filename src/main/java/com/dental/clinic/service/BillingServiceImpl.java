package com.dental.clinic.service;

import com.dental.clinic.dao.AppointmentDAO;
import com.dental.clinic.dao.AppointmentDAOImpl;
import com.dental.clinic.dao.BillDAO;
import com.dental.clinic.dao.BillDAOImpl;
import com.dental.clinic.model.Appointment;
import com.dental.clinic.model.Bill;

import java.time.LocalDate;
import java.util.List;

public class BillingServiceImpl implements BillingService {

    private final AppointmentDAO appointmentDAO;
    private final BillDAO billDAO;

    public BillingServiceImpl() {
        this.appointmentDAO = new AppointmentDAOImpl();
        this.billDAO = new BillDAOImpl();
    }

    public BillingServiceImpl(AppointmentDAO appointmentDAO, BillDAO billDAO) {
        this.appointmentDAO = appointmentDAO;
        this.billDAO = billDAO;
    }

    // Generate bill for appointment
    @Override
    public Bill generateBill(String appointmentNumber) {
        Bill existingBill = billDAO.findByAppointmentNumber(appointmentNumber);
        if (existingBill != null) {
            return existingBill;
        }

        Appointment appointment = appointmentDAO.findByAppointmentNumber(appointmentNumber);
        if (appointment == null) {
            throw new IllegalArgumentException("No appointment found with number: " + appointmentNumber);
        }

        if ("Cancelled".equals(appointment.getStatus())) {
            throw new IllegalArgumentException("Cannot generate a bill for a cancelled appointment.");
        }

        // Calculate bill amounts
        double consultationFee = appointment.getDentist().getConsultationFee();
        double treatmentCost = appointment.getTotalTreatmentCost();
        double totalAmount = consultationFee + treatmentCost;

        Bill bill = new Bill();
        bill.setBillId(generateBillId());
        bill.setAppointment(appointment);
        bill.setConsultationFee(consultationFee);
        bill.setTreatmentCost(treatmentCost);
        bill.setTotalAmount(totalAmount);
        bill.setBillDate(LocalDate.now());

        billDAO.save(bill);

        if (!"Completed".equals(appointment.getStatus())) {
            appointmentDAO.updateStatus(appointmentNumber, "Completed");
        }

        return bill;
    }

    //All bills
    @Override
    public List<Bill> getAllBills() {
        return billDAO.findAll();
    }

    //Generate bill ID
    private String generateBillId() {
        int nextNumber = billDAO.countAll() + 1;
        return String.format("BILL%03d", nextNumber);

    }
}
