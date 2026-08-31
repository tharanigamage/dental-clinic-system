package com.dental.clinic.service;

import com.dental.clinic.dao.UserDAO;
import com.dental.clinic.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;


public class FakeUserDAO implements UserDAO {

    private final List<User> users = new ArrayList<>();

    public void addUser(int userId, String username, String plainPassword, String role) {
        User user = new User();
        user.setUserId(userId);
        user.setUsername(username);
        user.setPassword(BCrypt.hashpw(plainPassword, BCrypt.gensalt()));
        user.setRole(role);
        users.add(user);
    }

    @Override
    public User findByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public User findById(int userId) {
        for (User u : users) {
            if (u.getUserId() == userId) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public void update(User user) {
    }

    @Override
    public void delete(int userId) {
    }

    @Override
    public int countByRole(String role) {
        int count = 0;
        for (User u : users) {
            if (u.getRole().equals(role)) count++;
        }
        return count;
    }
}