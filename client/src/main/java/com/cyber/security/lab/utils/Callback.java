package com.cyber.security.lab.utils;

public interface Callback {
    void onSuccess();

    void onFailure(Object errorMessage);
}
