package com.dental.clinic.service;

import com.dental.clinic.model.User;
import java.util.List;

public interface StaffService {

    // All staff users
    List<User> getAllStaff();

    //Add new staff user
    User addStaff(String username, String password, String role);

    // Update existing staff user
    User updateStaff(int userId, String username, String password, String role);

    //Update login password
    void updateOwnPassword (int userId, String currentPassword, String newPassword);

    // Delete staff user
    void deleteStaff(int userId);
}
