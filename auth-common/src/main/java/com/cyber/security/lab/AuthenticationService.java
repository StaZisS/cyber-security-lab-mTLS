package com.cyber.security.lab;


public interface AuthenticationService {
    boolean isCorrectCertificate(String certificate);
    String encryptMessage(String certificate, String message);
    String decryptMessage(String keystorePrivateKeyPath, String message);
}
