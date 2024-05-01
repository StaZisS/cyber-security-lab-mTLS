package com.cyber.security.lab;

import com.cyber.security.lab.body.RequestBody;
import io.netty.channel.ChannelHandlerContext;



public class ResponseUserUtils {
    public static void sendError(ChannelHandlerContext ctx, String errorMessage) {
        ctx.writeAndFlush(new RequestBody.RequestBodyBuilder()
                .setStatusCode(StatusCodeEnum.ERROR)
                .setBody(errorMessage).build()
        );
    }

    public static void sendOk(ChannelHandlerContext ctx, Object message) {
        ctx.writeAndFlush(new RequestBody.RequestBodyBuilder()
                .setStatusCode(StatusCodeEnum.OK)
                .setBody(message).build()
        );
    }
}
