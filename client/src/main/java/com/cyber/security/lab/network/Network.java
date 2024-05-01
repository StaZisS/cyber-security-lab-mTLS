package com.cyber.security.lab.network;

import com.cyber.security.lab.utils.Callback;
import com.cyber.security.lab.RequestBody;
import com.cyber.security.lab.ResponseBody;

import java.util.concurrent.CompletableFuture;

public interface Network {
    void openConnection(Callback callback);
    CompletableFuture<ResponseBody> sendMessageAndGetFuture(RequestBody msg);
    void closeConnection();
    void setAddress(String address);
    void setPort(int port);
}
