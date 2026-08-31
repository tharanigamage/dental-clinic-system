package com.dental.clinic.service;

import com.dental.clinic.model.Appointment;
import com.dental.clinic.model.Bill;
import com.dental.clinic.model.Dentist;
import com.dental.clinic.model.Patient;
import com.dental.clinic.model.TreatmentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BillingServiceImplTest {

    private FakeAppointmentDAO fakeAppointmentDAO;
    private FakeBillDAO fakeBillDAO;
    private BillingService billingService;

    @BeforeEach
    void setUp() {
        fakeAppointmentDAO = new FakeAppointmentDAO();
        fakeBillDAO = new FakeBillDAO();
        billingService = new BillingServiceImpl(fakeAppointmentDAO, fakeBillDAO);
    }

    private Appointment createTestAppointment(String status) {
        Patient patient = new Patient();
        patient.setPatientId(1);
        patient.setName("Kasun Perera");
        patient.setContactNumber("0771234567");

        Dentist dentist = new Dentist();
        dentist.setDentistId(1);
        dentist.setName("Dr. Perera");
        dentist.setConsultationFee(1500.0);

        TreatmentType scaling = new TreatmentType();
        scaling.setTreatmentId(1);
        scaling.setTreatmentName("Scaling");
        scaling.setCost(3000.0);

        Appointment appointment = new Appointment();
        appointment.setAppointmentNumber("APT001");
        appointment.setPatient(patient);
        appointment.setDentist(dentist);
        appointment.setTreatmentTypes(List.of(scaling));
        appointment.setAppointmentDate(LocalDate.now());
        appointment.setAppointmentTime(LocalTime.of(9, 0));
        appointment.setStatus(status);

        fakeAppointmentDAO.save(appointment);
        return appointment;
    }

    @Test
    void generateBill_succeeds_forPendingAppointment() {
        createTestAppointment("Pending");

        Bill bill = billingService.generateBill("APT001");

        assertNotNull(bill);
        assertEquals("BILL001", bill.getBillId());
        assertEquals(1500.0, bill.getConsultationFee());
        assertEquals(3000.0, bill.getTreatmentCost());
        assertEquals(4500.0, bill.getTotalAmount());
    }

    @Test
    void generateBill_marksAppointmentAsCompleted() {
        createTestAppointment("Pending");

        billingService.generateBill("APT001");

        Appointment updated = fakeAppointmentDAO.findByAppointmentNumber("APT001");
        assertEquals("Completed", updated.getStatus());
    }

    @Test
    void generateBill_returnsExistingBill_ifAlreadyGenerated() {
        createTestAppointment("Pending");

        Bill firstCall = billingService.generateBill("APT001");
        Bill secondCall = billingService.generateBill("APT001");

        assertEquals(firstCall.getBillId(), secondCall.getBillId());
        assertEquals(1, fakeBillDAO.countAll());
    }

    @Test
    void generateBill_throws_forCancelledAppointment() {
        createTestAppointment("Cancelled");

        assertThrows(IllegalArgumentException.class, () ->
                billingService.generateBill("APT001"));
    }

    @Test
    void generateBill_throws_whenAppointmentNotFound() {
        assertThrows(IllegalArgumentException.class, () ->
                billingService.generateBill("APT999"));
    }

    @Test
    void getAllBills_returnsAllGeneratedBills() {
        createTestAppointment("Pending");
        billingService.generateBill("APT001");

        List<Bill> allBills = billingService.getAllBills();

        assertEquals(1, allBills.size());
    }
}