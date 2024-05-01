package com.cyber.security.lab;

public enum ResponseTypeEnum {
    MESSAGE("message", true),
    AUTH("auth", false),
    ;

    final String value;
    final boolean isAuthRequired;

    ResponseTypeEnum(String value, boolean isAuthRequired) {
        this.value = value;
        this.isAuthRequired = isAuthRequired;
    }

    public String getValue() {
        return value;
    }

    public boolean isAuthRequired() {
        return isAuthRequired;
    }
}
