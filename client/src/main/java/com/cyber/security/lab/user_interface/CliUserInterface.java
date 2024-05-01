package com.cyber.security.lab.user_interface;

import com.cyber.security.lab.public_interface.ConnectionDto;

public class CliUserInterface implements UserInterface {
    @Override
    public ConnectionDto getConnection() {
        while (true) {
            try {
                System.out.println("Enter the server host:");
                var host = System.console().readLine();
                System.out.println("Enter the server port:");
                var port = Integer.parseInt(System.console().readLine());
                return new ConnectionDto(host, port);
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");

            }
        }
    }

    @Override
    public void showErrorMessage(String message) {
        System.err.println(message);
    }

    @Override
    public void successMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String getMessage() {
        return System.console().readLine();
    }
}
