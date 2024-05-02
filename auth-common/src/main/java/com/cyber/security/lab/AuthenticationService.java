package com.cyber.security.lab;


public interface AuthenticationService {
    boolean isCorrectCertificate(String certificate, String publicKey);
    String encryptMessage(String publicKey, String message);
    String decryptMessage(String privateKey, String message);
}
