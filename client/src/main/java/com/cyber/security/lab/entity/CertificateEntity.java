package com.cyber.security.lab.entity;

public record CertificateEntity(
        String certificate,
        String publicKey,
        String privateKeyPath
) {
}
