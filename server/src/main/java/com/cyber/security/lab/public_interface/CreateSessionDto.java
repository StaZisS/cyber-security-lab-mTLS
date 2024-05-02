package com.cyber.security.lab.public_interface;

public record CreateSessionDto(
        String sessionId,
        String certificate,
        String publicKey
) {
}
