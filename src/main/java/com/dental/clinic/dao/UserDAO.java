package com.dental.clinic.dao;

import com.dental.clinic.model.User;
import java.util.List;

public interface UserDAO {

    // Find by username
    User findByUsername (String username);

    // Find by id
    User findById(int userId);

    // All users
    List<User> findAll();

    // Save new user
    void save(User user);

    // Update user
    void update(User user);

    // Delete user
    void delete(int userId);

    //Count by role
    int countByRole(String role);
}
