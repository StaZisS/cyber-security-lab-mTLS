package com.cyber.security.lab;

public record ServerCheckResponseDto(
        String encryptedMessage,
        String certificate,
        String publicKey
) {
}
