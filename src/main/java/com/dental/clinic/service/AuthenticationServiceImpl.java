package com.dental.clinic.service;

import com.dental.clinic.dao.UserDAO;
import com.dental.clinic.dao.UserDAOImpl;
import com.dental.clinic.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDAO userDAO;

    public AuthenticationServiceImpl(){
        this.userDAO = new UserDAOImpl();
    }

    public AuthenticationServiceImpl(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    // Authenticate username and password
    @Override
    public User login(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isEmpty()){
            return null;
        }

        User user = userDAO.findByUsername(username);

        if (user == null){
            return null;
        }

        if (!BCrypt.checkpw(password, user.getPassword())){
            return null;
        }
        return user;
    }
}
