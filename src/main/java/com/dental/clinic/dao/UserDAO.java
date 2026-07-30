package com.dental.clinic.dao;

import com.dental.clinic.model.User;

public interface UserDAO {
    User findByUsername (String username);
}
