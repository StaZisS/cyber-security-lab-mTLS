package com.cyber.security.lab.entity;

public record SessionEntity(
        String sessionId,
        boolean isAuthorized,
        String certificate,
        String publicKey,
        String testMessage
) {
}
