package com.cyber.security.lab;

public record ServerCheckRequestDto(
        String certificate,
        String publicKey
) {
}
