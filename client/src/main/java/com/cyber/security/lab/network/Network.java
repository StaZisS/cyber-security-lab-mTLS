package com.cyber.security.lab.network;

import com.cyber.security.lab.body.RequestBody;
import com.cyber.security.lab.body.ResponseBody;
import com.cyber.security.lab.utils.Callback;

import java.util.concurrent.CompletableFuture;

public interface Network {
    void openConnection(Callback callback);
    CompletableFuture<ResponseBody> sendMessageAndGetFuture(RequestBody msg);
    void closeConnection();
    void setAddress(String address);
    void setPort(int port);
}
