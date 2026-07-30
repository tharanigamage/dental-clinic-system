package com.dental.clinic.service;

import com.dental.clinic.model.User;

public interface AuthenticationService {

    User login (String username , String password);

}
