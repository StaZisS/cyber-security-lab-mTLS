package com.cyber.security.lab.service;


public interface AuthenticationService {
    boolean isCorrectCertificate(String certificate, String publicKey);
    String encryptMessage(String publicKey, String message);
    String decryptMessage(String privateKey, String message);
}
