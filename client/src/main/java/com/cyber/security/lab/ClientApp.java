package com.cyber.security.lab;

import com.cyber.security.lab.body.RequestBody;
import com.cyber.security.lab.config.BillingModule;
import com.cyber.security.lab.network.Network;
import com.cyber.security.lab.repository.CertificateRepository;
import com.cyber.security.lab.user_interface.UserInterface;
import com.cyber.security.lab.utils.Callback;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.Random;

public class ClientApp {
    private static final Random RANDOM = new Random();
    protected Injector injector;
    protected UserInterface userInterface;
    protected Network network;
    protected AuthenticationService authenticationService;
    protected CertificateRepository certificateRepository;

    public static void main(String[] args) {
        new ClientApp().start();
    }

    public void start() {
        injector = Guice.createInjector(new BillingModule());
        userInterface = injector.getInstance(UserInterface.class);
        network = injector.getInstance(Network.class);
        authenticationService = injector.getInstance(AuthenticationService.class);
        certificateRepository = injector.getInstance(CertificateRepository.class);
        tryToConnect();
    }

    private void tryToConnect() {
        var connectionDto = userInterface.getConnection();
        network.setAddress(connectionDto.host());
        network.setPort(connectionDto.port());
        network.openConnection(new Callback() {
            @Override
            public void onSuccess() {
                userInterface.successMessage("Connected to the server.");
                serverCheck();
            }

            @Override
            public void onFailure(Object errorMessage) {
                userInterface.showErrorMessage("Failed to connect to the server. Reason: %s. Please try again.".formatted(errorMessage));
                tryToConnect();
            }
        });
    }

    private void serverCheck() {
        var certificate = certificateRepository.getClientCertificate();
        var serverCheckDto = new ServerCheckRequestDto(certificate.certificate(), certificate.publicKey());
        var requestBody = new RequestBody(ResponseTypeEnum.SERVER_CHECK, serverCheckDto);

        network.sendMessageAndGetFuture(requestBody)
                .thenAccept(responseBody -> {
                    if (responseBody.getStatusCode() == StatusCodeEnum.OK) {
                        userInterface.successMessage("Server check successful.");
                        ServerCheckResponseDto serverCheckResponseDto = JsonUtils.fromJson(responseBody.getBody(), ServerCheckResponseDto.class);

                        certificateRepository.setServerCertificate(serverCheckResponseDto.certificate());
                        certificateRepository.setServerPublicKey(serverCheckResponseDto.publicKey());
                        clientCheck(serverCheckResponseDto);
                    } else {
                        userInterface.showErrorMessage("Server check failed. Reason: %s.".formatted(responseBody.getBody()));
                    }
                })
                .exceptionally(throwable -> {
                    userInterface.showErrorMessage("Failed to authenticate. Reason: %s. Please try again.".formatted(throwable.getMessage()));
                    tryToConnect();
                    return null;
                });
    }

    private void clientCheck(ServerCheckResponseDto dto) {
        var certificate = certificateRepository.getClientCertificate();
        var randomString = getRandomString();
        var decryptedServerMessage = authenticationService.decryptMessage(certificate.privateKey(), dto.encryptedMessage());
        var encryptedClientMessage = authenticationService.encryptMessage(dto.publicKey(), randomString);

        var clientCheckDto = new ClientCheckRequestDto(
                decryptedServerMessage,
                encryptedClientMessage
        );
        var requestBody = new RequestBody(ResponseTypeEnum.CLIENT_CHECK, clientCheckDto);

        network.sendMessageAndGetFuture(requestBody)
                .thenAccept(responseBody -> {
                    if (responseBody.getStatusCode() == StatusCodeEnum.OK) {
                        ClientCheckResponseDto clientCheckResponseDto = JsonUtils.fromJson(responseBody.getBody(), ClientCheckResponseDto.class);
                        if (clientCheckResponseDto.decryptedClientMessage().equals(randomString)) {
                            userInterface.successMessage("Client check successful.");
                            handleMessage();
                        } else {
                            userInterface.showErrorMessage("Client check failed. Reason: Invalid decrypted message.");
                        }
                    } else {
                        userInterface.showErrorMessage("Client check failed. Reason: %s.".formatted(responseBody.getBody()));
                    }
                })
                .exceptionally(throwable -> {
                    userInterface.showErrorMessage("Failed to authenticate. Reason: %s. Please try again.".formatted(throwable.getMessage()));
                    tryToConnect();
                    return null;
                });
    }

    private void handleMessage() {
        userInterface.successMessage("Enter the message to send:");
        var clientCertificate = certificateRepository.getClientCertificate();
        var serverCertificate = certificateRepository.getServerCertificate();

        var msg = userInterface.getMessage();
        var encryptedMessage = authenticationService.encryptMessage(serverCertificate.publicKey(), msg);
        var dto = new MessageRequestDto(encryptedMessage);
        var request = new RequestBody(ResponseTypeEnum.MESSAGE, dto);

        network.sendMessageAndGetFuture(request).thenAccept(responseBody -> {
            var isSuccessful = responseBody.getStatusCode() == StatusCodeEnum.OK;
            if (isSuccessful) {
                userInterface.successMessage("Message sent successfully.");

                MessageResponseDto responseDto = JsonUtils.fromJson(responseBody.getBody(), MessageResponseDto.class);
                var decryptedMessage = authenticationService.decryptMessage(clientCertificate.privateKey(), responseDto.encryptedServerMessage());

                userInterface.successMessage("Response from the server: %s".formatted(decryptedMessage));
                handleMessage();
            } else {
                userInterface.showErrorMessage("Failed to send the message. Reason: %s.".formatted(responseBody.getBody()));
            }
        });
    }

    private String getRandomString() {
        return RANDOM.ints(48, 122)
                .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
