package com.cyber.security.lab;

public enum StatusCodeEnum {
    OK(0),
    ERROR(1);
    final int value;

    StatusCodeEnum(int value) {
        this.value = value;
    }
}
