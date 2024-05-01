package com.cyber.security.lab;

import com.cyber.security.lab.handler.CommandHandler;
import com.cyber.security.lab.handler.CommandRouter;
import com.cyber.security.lab.handler.CommandType;
import com.cyber.security.lab.service.SessionManager;
import com.google.inject.Inject;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    private final Map<ResponseTypeEnum, CommandHandler> handlers;
    private final SessionManager sessionManager;

    @Inject
    public ServerInitializer(Set<CommandHandler> commandHandlers, SessionManager sessionManager) {
        this.handlers = commandHandlers.stream()
                .collect(Collectors.toMap(handler -> {
                    CommandType annotation = handler.getClass().getAnnotation(CommandType.class);
                    if (annotation != null) {
                        return annotation.value();
                    }
                    throw new IllegalArgumentException("Command handler is missing CommandType annotation");
                }, Function.identity()));
        this.sessionManager = sessionManager;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new JsonDecoder(), new JsonEncoder());
        socketChannel.pipeline().addLast(new ChunkedWriteHandler());
        socketChannel.pipeline().addLast(new CommandRouter(handlers, sessionManager));
    }
}