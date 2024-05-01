package com.cyber.security.lab.handler.business_logic;

import com.cyber.security.lab.ResponseTypeEnum;
import com.cyber.security.lab.handler.CommandHandler;
import com.cyber.security.lab.handler.CommandType;
import io.netty.channel.ChannelHandlerContext;

@CommandType(ResponseTypeEnum.MESSAGE)
public class MessageHandler implements CommandHandler {

    @Override
    public void handle(Object body, ChannelHandlerContext ctx) {

    }
}
