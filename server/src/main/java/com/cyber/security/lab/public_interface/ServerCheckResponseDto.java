package com.cyber.security.lab.public_interface;

public record ServerCheckResponseDto(
        String encryptedMessage,
        String certificate,
        String publicKey
) {
}
