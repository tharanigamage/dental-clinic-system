package com.dental.clinic.service;

import com.dental.clinic.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceImplTest {

    private FakeUserDAO fakeUserDAO;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        fakeUserDAO = new FakeUserDAO();
        authenticationService = new AuthenticationServiceImpl(fakeUserDAO);

        fakeUserDAO.addUser(1, "admin", "admin123", "Admin");
    }

    @Test
    void login_succeeds_withCorrectCredentials() {
        User result = authenticationService.login("admin", "admin123");

        assertNotNull(result);
        assertEquals("admin", result.getUsername());
        assertEquals("Admin", result.getRole());
    }

    @Test
    void login_fails_withWrongPassword() {
        User result = authenticationService.login("admin", "wrongPassword");

        assertNull(result);
    }

    @Test
    void login_fails_withUnknownUsername() {
        User result = authenticationService.login("nonexistentUser", "admin123");

        assertNull(result);
    }

    @Test
    void login_fails_withNullUsername() {
        User result = authenticationService.login(null, "admin123");

        assertNull(result);
    }

    @Test
    void login_fails_withNullPassword() {
        User result = authenticationService.login("admin", null);

        assertNull(result);
    }

    @Test
    void login_fails_withBlankUsername() {
        User result = authenticationService.login("   ", "admin123");

        assertNull(result);
    }

    @Test
    void login_fails_withEmptyPassword() {
        User result = authenticationService.login("admin", "");

        assertNull(result);
    }

    @Test
    void login_isCaseSensitive_forPassword() {
        User result = authenticationService.login("admin", "Admin123");

        assertNull(result);
    }
}