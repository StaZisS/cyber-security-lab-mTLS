package com.cyber.security.lab.handler;

import io.netty.channel.ChannelHandlerContext;

public interface CommandHandler {
    void handle(Object body, ChannelHandlerContext ctx);
}
