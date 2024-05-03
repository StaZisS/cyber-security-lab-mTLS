package com.cyber.security.lab.user_interface;

import com.cyber.security.lab.public_interface.ConnectionDto;

import java.util.Scanner;

public class CliUserInterface implements UserInterface {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public ConnectionDto getConnection() {
        while (true) {
            try {
                System.out.println("Enter the server host:");
                var host = scanner.nextLine();
                System.out.println("Enter the server port:");
                var port = Integer.parseInt(scanner.nextLine());
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
        return scanner.nextLine();
    }
}
