package com.dental.clinic.service;

import com.dental.clinic.model.User;

public interface AuthenticationService {

    // Username and password
    User login (String username , String password);

}
