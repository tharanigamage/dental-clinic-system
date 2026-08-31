package com.dental.clinic.service;

import com.dental.clinic.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaffServiceImplTest {

    private FakeUserDAO fakeUserDAO;
    private StaffService staffService;

    @BeforeEach
    void setUp() {
        fakeUserDAO = new FakeUserDAO();
        staffService = new StaffServiceImpl(fakeUserDAO);

        fakeUserDAO.addUser(1, "admin", "admin123", "Admin");
    }

    @Test
    void addStaff_succeeds_withValidData() {
        User result = staffService.addStaff("reception1", "pass123", "Receptionist");

        assertNotNull(result);
        assertEquals("reception1", result.getUsername());
        assertEquals("Receptionist", result.getRole());
        assertNotEquals("pass123", result.getPassword());
    }

    @Test
    void addStaff_throws_whenUsernameAlreadyTaken() {
        staffService.addStaff("reception1", "pass123", "Receptionist");

        assertThrows(IllegalArgumentException.class, () ->
                staffService.addStaff("reception1", "otherPass", "Admin"));
    }

    @Test
    void addStaff_throws_whenPasswordTooShort() {
        assertThrows(IllegalArgumentException.class, () ->
                staffService.addStaff("reception1", "abc", "Receptionist"));
    }

    @Test
    void addStaff_throws_whenRoleIsInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                staffService.addStaff("reception1", "pass123", "SuperAdmin"));
    }

    @Test
    void updateStaff_succeeds_whenChangingUsername() {
        User result = staffService.updateStaff(1, "adminNew", null, "Admin");

        assertEquals("adminNew", result.getUsername());
    }

    @Test
    void updateStaff_throws_whenDemotingTheLastAdmin() {
        assertThrows(IllegalArgumentException.class, () ->
                staffService.updateStaff(1, "admin", null, "Receptionist"));
    }

    @Test
    void updateStaff_allowsDemotingAdmin_whenAnotherAdminExists() {
        fakeUserDAO.addUser(2, "admin2", "pass123", "Admin");

        User result = staffService.updateStaff(1, "admin", null, "Receptionist");

        assertEquals("Receptionist", result.getRole());
    }

    @Test
    void deleteStaff_removesUser() {
        staffService.addStaff("reception1", "pass123", "Receptionist");

        staffService.deleteStaff(2);

        assertNull(fakeUserDAO.findById(2));
    }
}