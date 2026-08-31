package com.dental.clinic.service;

import com.dental.clinic.model.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentServiceImplTest {

    private FakePatientDAO fakePatientDAO;
    private FakeDentistDAO fakeDentistDAO;
    private FakeTreatmentTypeDAO fakeTreatmentTypeDAO;
    private FakeAppointmentDAO fakeAppointmentDAO;
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        fakePatientDAO = new FakePatientDAO();
        fakeDentistDAO = new FakeDentistDAO();
        fakeTreatmentTypeDAO = new FakeTreatmentTypeDAO();
        fakeAppointmentDAO = new FakeAppointmentDAO();

        appointmentService = new AppointmentServiceImpl(
                fakePatientDAO, fakeDentistDAO, fakeTreatmentTypeDAO, fakeAppointmentDAO);

        fakeDentistDAO.addDentist(1, "Dr. Perera", "General Dentistry", 1500.0);
        fakeTreatmentTypeDAO.addTreatment(1, "Scaling", 3000.0);
        fakeTreatmentTypeDAO.addTreatment(2, "Filling", 4500.0);
    }

    @Test
    void registerAppointment_succeeds_withValidData() {
        Appointment result = appointmentService.registerAppointment(
                "912345678V", "Kasun Perera", "Colombo", "0771234567",
                1, List.of(1), LocalDate.now().plusDays(1), LocalTime.of(9, 0));

        assertNotNull(result);
        assertEquals("APT001", result.getAppointmentNumber());
        assertEquals("Pending", result.getStatus());
        assertEquals(1, result.getTreatmentTypes().size());
    }

    @Test
    void registerAppointment_reusesExistingPatient_whenNicAlreadyRegistered() {
        appointmentService.registerAppointment(
                "912345678V", "Kasun Perera", "Colombo", "0771234567",
                1, List.of(1), LocalDate.now().plusDays(1), LocalTime.of(9, 0));

        appointmentService.registerAppointment(
                "912345678V", "Kasun Perera", "Colombo", "0771234567",
                1, List.of(2), LocalDate.now().plusDays(2), LocalTime.of(10, 0));

        assertEquals(1, fakePatientDAO.findAll().size());
    }

    @Test
    void registerAppointment_throws_whenNicIsBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                appointmentService.registerAppointment(
                        "", "Kasun Perera", "Colombo", "0771234567",
                        1, List.of(1), LocalDate.now().plusDays(1), LocalTime.of(9, 0)));
    }

    @Test
    void registerAppointment_throws_whenNameIsInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                appointmentService.registerAppointment(
                        "912345678V", "K1", "Colombo", "0771234567",
                        1, List.of(1), LocalDate.now().plusDays(1), LocalTime.of(9, 0)));
    }

    @Test
    void registerAppointment_throws_whenContactNumberIsInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                appointmentService.registerAppointment(
                        "912345678V", "Kasun Perera", "Colombo", "12345",
                        1, List.of(1), LocalDate.now().plusDays(1), LocalTime.of(9, 0)));
    }

    @Test
    void registerAppointment_throws_whenNoTreatmentSelected() {
        assertThrows(IllegalArgumentException.class, () ->
                appointmentService.registerAppointment(
                        "912345678V", "Kasun Perera", "Colombo", "0771234567",
                        1, List.of(), LocalDate.now().plusDays(1), LocalTime.of(9, 0)));
    }

    @Test
    void registerAppointment_throws_whenDateIsInThePast() {
        assertThrows(IllegalArgumentException.class, () ->
                appointmentService.registerAppointment(
                        "912345678V", "Kasun Perera", "Colombo", "0771234567",
                        1, List.of(1), LocalDate.now().minusDays(1), LocalTime.of(9, 0)));
    }

    @Test
    void registerAppointment_throws_whenDentistAlreadyBookedAtSameSlot() {
        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime time = LocalTime.of(9, 0);

        appointmentService.registerAppointment(
                "912345678V", "Kasun Perera", "Colombo", "0771234567",
                1, List.of(1), date, time);

        assertThrows(IllegalArgumentException.class, () ->
                appointmentService.registerAppointment(
                        "912345679X", "Nimal Silva", "Kandy", "0779876543",
                        1, List.of(2), date, time));
    }

    @Test
    void registerAppointment_allowsDifferentTimeSlot_forSameDentist() {
        LocalDate date = LocalDate.now().plusDays(1);

        appointmentService.registerAppointment(
                "912345678V", "Kasun Perera", "Colombo", "0771234567",
                1, List.of(1), date, LocalTime.of(9, 0));

        Appointment second = appointmentService.registerAppointment(
                "912345679X", "Nimal Silva", "Kandy", "0779876543",
                1, List.of(2), date, LocalTime.of(10, 0));

        assertNotNull(second);
        assertEquals("APT002", second.getAppointmentNumber());
    }

    @Test
    void registerAppointment_supportsMultipleTreatments() {
        Appointment result = appointmentService.registerAppointment(
                "912345678V", "Kasun Perera", "Colombo", "0771234567",
                1, List.of(1, 2), LocalDate.now().plusDays(1), LocalTime.of(9, 0));

        assertEquals(2, result.getTreatmentTypes().size());
        assertEquals(7500.0, result.getTotalTreatmentCost());
    }

    @Test
    void updateAppointmentStatus_changesStatus() {
        Appointment appointment = appointmentService.registerAppointment(
                "912345678V", "Kasun Perera", "Colombo", "0771234567",
                1, List.of(1), LocalDate.now().plusDays(1), LocalTime.of(9, 0));

        appointmentService.updateAppointmentStatus(appointment.getAppointmentNumber(), "Completed");

        Appointment updated = appointmentService.searchAppointment(appointment.getAppointmentNumber());
        assertEquals("Completed", updated.getStatus());
    }
}