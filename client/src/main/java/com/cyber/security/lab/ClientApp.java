package com.cyber.security.lab;

import com.cyber.security.lab.body.RequestBody;
import com.cyber.security.lab.config.BillingModule;
import com.cyber.security.lab.network.Network;
import com.cyber.security.lab.user_interface.UserInterface;
import com.cyber.security.lab.utils.Callback;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ClientApp {
    protected Injector injector;
    protected UserInterface userInterface;
    protected Network network;

    public static void main(String[] args) {
        new ClientApp().start();
    }

    public void start() {
        injector = Guice.createInjector(new BillingModule());
        userInterface = injector.getInstance(UserInterface.class);
        network = injector.getInstance(Network.class);
        tryToConnect();
        handleMessage();
    }

    private void tryToConnect() {
        var connectionDto = userInterface.getConnection();
        network.setAddress(connectionDto.host());
        network.setPort(connectionDto.port());
        network.openConnection(new Callback() {
            @Override
            public void onSuccess() {
                userInterface.successMessage("Connected to the server.");
            }

            @Override
            public void onFailure(Object errorMessage) {
                userInterface.showErrorMessage("Failed to connect to the server. Reason: %s. Please try again.".formatted(errorMessage));
                tryToConnect();
            }
        });
    }

    private void handleMessage() {
        userInterface.successMessage("Enter the message to send:");
        var msg = userInterface.getMessage();
        var request = new RequestBody(ResponseTypeEnum.MESSAGE, msg);
        network.sendMessageAndGetFuture(request).thenAccept(responseBody -> {
            var isSuccessful = responseBody.getStatusCode() == StatusCodeEnum.OK;
            if (isSuccessful) {
                userInterface.successMessage("Message sent successfully.");
                userInterface.successMessage("Response from the server: %s".formatted(responseBody.getBody()));
                handleMessage();
            } else {
                userInterface.showErrorMessage("Failed to send the message. Reason: %s.".formatted(responseBody.getBody()));
            }
        });
    }
}
