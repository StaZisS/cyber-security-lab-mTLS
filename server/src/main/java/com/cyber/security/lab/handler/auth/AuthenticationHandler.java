package com.cyber.security.lab.handler.auth;

import com.cyber.security.lab.ResponseTypeEnum;
import com.cyber.security.lab.handler.CommandHandler;
import com.cyber.security.lab.handler.CommandType;
import com.cyber.security.lab.service.AuthenticationService;
import com.cyber.security.lab.service.SessionManager;
import com.google.inject.Inject;
import io.netty.channel.ChannelHandlerContext;

@CommandType(ResponseTypeEnum.AUTH)
public class AuthenticationHandler implements CommandHandler {
    private final AuthenticationService authService;
    private final SessionManager sessionManager;

    @Inject
    public AuthenticationHandler(AuthenticationService authenticationService, SessionManager sessionManager) {
        this.authService = authenticationService;
        this.sessionManager = sessionManager;
    }

    @Override
    public void handle(Object body, ChannelHandlerContext ctx) {

    }
}
