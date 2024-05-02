package com.cyber.security.lab;

public record ClientCheckRequestDto(
        String decryptedServerMessage,
        String encryptedClientMessage
) {
}
