package com.cyber.security.lab.service;

import com.cyber.security.lab.public_interface.CreateSessionDto;
import io.netty.channel.ChannelHandlerContext;

public interface SessionService {
    static String getSessionId(ChannelHandlerContext ctx) {
        return ctx.channel().id().asLongText();
    }

    void createSession(CreateSessionDto dto);

    void deleteSession(String sessionId);

    boolean isSessionAuthenticated(String sessionId);

    void setSessionAuthenticated(String sessionId, boolean isAuthorized);
    void setCertificateAndPublicKey(String sessionId, String certificate, String publicKey);
    void setTestMessage(String sessionId, String testMessage);
    String getTestMessage(String sessionId);
}
