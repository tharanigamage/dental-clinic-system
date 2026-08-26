package com.dental.clinic.service;

import com.dental.clinic.model.User;
import java.util.List;

public interface StaffService {

    List<User> getAllStaff();

    User addStaff(String username, String password, String role);

    User updateStaff(int userId, String username, String password, String role);

    void updateOwnPassword (int userId, String currentPassword, String newPassword);

    void deleteStaff(int userId);
}
