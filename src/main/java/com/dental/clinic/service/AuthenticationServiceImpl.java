package com.dental.clinic.service;

import com.dental.clinic.dao.UserDAO;
import com.dental.clinic.dao.UserDAOImpl;
import com.dental.clinic.model.User;

public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDAO userDAO;

    public AuthenticationServiceImpl(){
        this.userDAO = new UserDAOImpl();
    }

    public AuthenticationServiceImpl(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isEmpty()){
            return null;
        }

        User user = userDAO.findByUsername(username);

        if (user == null){
            return null;
        }

        if (!user.getPassword().equals(password)){
            return null;
        }
        return user;
    }
}
