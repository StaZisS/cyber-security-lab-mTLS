package com.cyber.security.lab.public_interface;

public record ClientCheckRequestDto(
        String decryptedServerMessage,
        String encryptedClientMessage
) {
}
