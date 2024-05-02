package com.cyber.security.lab;

public class AuthenticationServiceImpl implements AuthenticationService {

    //TODO: проверить цировую подпись
    @Override
    public boolean isCorrectCertificate(String certificate, String publicKey) {
        return false;
    }

    //TODO: зашифровать сообщение
    @Override
    public String encryptMessage(String publicKey, String message) {
        return null;
    }

    @Override
    public String decryptMessage(String privateKey, String message) {
        return null;
    }
}
