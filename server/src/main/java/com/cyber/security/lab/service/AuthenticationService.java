package com.cyber.security.lab.service;

import org.example.exception.UserAlreadyExistsException;

public interface AuthenticationService {
    boolean login(String username, String password);

    void register(String username, String password) throws UserAlreadyExistsException;
}
