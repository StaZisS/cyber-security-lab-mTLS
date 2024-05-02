package com.cyber.security.lab.public_interface;

public record ServerCheckRequestDto(
        String certificate,
        String publicKey
) {
}
