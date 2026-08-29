package com.dental.clinic.service;

import com.dental.clinic.dao.UserDAO;
import com.dental.clinic.dao.UserDAOImpl;
import com.dental.clinic.model.User;
import com.dental.clinic.util.ValidationUtil;

import java.util.List;

public class StaffServiceImpl implements StaffService{

    private final UserDAO userDAO;

    public StaffServiceImpl(){
        this.userDAO = new UserDAOImpl();
    }

    public StaffServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getAllStaff() {
        return userDAO.findAll();
    }

    @Override
    public User addStaff(String username, String password, String role) {

        if (ValidationUtil.isNullorBlank(username)) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (ValidationUtil.isNullorBlank(password) || password.trim().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters.");
        }
        if (!"Admin".equals(role) && !"Receptionist".equals(role)) {
            throw new IllegalArgumentException("Role must be either Admin or Receptionist.");
        }

        User existing = userDAO.findByUsername(username.trim());
        if (existing != null) {
            throw new IllegalArgumentException("This username is already taken. Please choose another.");
        }

        User user = new User();
        user.setUsername(username.trim());
        user.setPassword(password.trim());
        user.setRole(role);

        userDAO.save(user);

        return user;
    }

    @Override
    public User updateStaff(int userId, String username, String password, String role) {

        User existingUser = userDAO.findById(userId);
        if (existingUser == null) {
            throw new IllegalArgumentException("Staff account not found.");
        }

        if (ValidationUtil.isNullorBlank(username)) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (!"Admin".equals(role) && !"Receptionist".equals(role)) {
            throw new IllegalArgumentException("Role must be either Admin or Receptionist.");
        }

        boolean isDemotingAdmin = "Admin".equals(existingUser.getRole()) && !"Admin".equals(role);
        if (isDemotingAdmin && userDAO.countByRole("Admin") <= 1) {
            throw new IllegalArgumentException("Cannot change the role of the last remaining Admin account.");
        }

        User accountWithSameUsername = userDAO.findByUsername(username.trim());
        if (accountWithSameUsername != null && accountWithSameUsername.getUserId() != userId) {
            throw new IllegalArgumentException("This username is already taken. Please choose another.");
        }

        existingUser.setUsername(username.trim());
        existingUser.setRole(role);


        if (!ValidationUtil.isNullorBlank(password)) {
            if (password.trim().length() < 4) {
                throw new IllegalArgumentException("New password must be at least 4 characters.");
            }
            existingUser.setPassword(password.trim());
        }

        userDAO.update(existingUser);

        return existingUser;
    }

    @Override
    public void updateOwnPassword(int userId, String currentPassword, String newPassword) {

        User user = userDAO.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Account not found.");
        }

        if (!user.getPassword().equals(currentPassword)) {
            throw new IllegalArgumentException("Current password is incorrect.");
        }

        if (ValidationUtil.isNullorBlank(newPassword) || newPassword.trim().length() < 4) {
            throw new IllegalArgumentException("New password must be at least 4 characters.");
        }

        user.setPassword(newPassword.trim());
        userDAO.update(user);
    }

    @Override
    public void deleteStaff(int userId) {
        userDAO.delete(userId);
    }


}

