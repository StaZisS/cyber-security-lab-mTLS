package com.cyber.security.lab.user_interface;

import com.cyber.security.lab.public_interface.ConnectionDto;

public interface UserInterface {
    ConnectionDto getConnection();
    void showErrorMessage(String message);
    void successMessage(String message);
    String getMessage();
}
