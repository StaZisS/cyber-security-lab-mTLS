package com.cyber.security.lab.handler;

import com.cyber.security.lab.JsonUtils;
import com.cyber.security.lab.ResponseTypeEnum;
import com.cyber.security.lab.ResponseUserUtils;
import com.cyber.security.lab.body.ResponseBody;
import com.cyber.security.lab.service.SessionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


import java.util.Map;

public class CommandRouter extends SimpleChannelInboundHandler<ResponseBody> {
    private final Map<ResponseTypeEnum, CommandHandler> handlers;
    private final SessionManager sessionManager;

    public CommandRouter(Map<ResponseTypeEnum, CommandHandler> handlers, SessionManager sessionManager) {
        this.handlers = handlers;
        this.sessionManager = sessionManager;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        var sessionId = SessionManager.getSessionId(ctx);
        sessionManager.createSession(sessionId);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseBody msg) throws Exception {
        var type = JsonUtils.getTypeResponseEnum(msg.getTypeRequest());
        if (type == null) {
            ResponseUserUtils.sendError(ctx, "Unknown type: " + msg.getTypeRequest());
            return;
        }

        if (type.isAuthRequired()) {
            var sessionId = SessionManager.getSessionId(ctx);
            if (!sessionManager.isSessionAuthenticated(sessionId)) {
                ResponseUserUtils.sendError(ctx, "User is not authorized");
                return;
            }
        }

        var handler = handlers.get(type);

        handler.handle(msg.getBody(), ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        var sessionId = SessionManager.getSessionId(ctx);
        sessionManager.deleteSession(sessionId);
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        var sessionId = SessionManager.getSessionId(ctx);
        sessionManager.deleteSession(sessionId);
    }
}
