package com.dental.clinic.dao;

import com.dental.clinic.model.User;
import java.util.List;

public interface UserDAO {

    User findByUsername (String username);

    User findById(int userId);

    List<User> findAll();

    void save(User user);

    void update(User user);

    void delete(int userId);

    int countByRole(String role);
}
