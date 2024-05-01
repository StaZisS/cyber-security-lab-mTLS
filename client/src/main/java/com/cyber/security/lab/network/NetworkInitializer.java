package com.cyber.security.lab.network;

import com.cyber.security.lab.JsonDecoder;
import com.cyber.security.lab.JsonEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.stream.ChunkedWriteHandler;

public class NetworkInitializer extends ChannelInitializer<SocketChannel> {
    private static final int MIN_BUFFER_SIZE = 64 * 1024;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        var channelConfig = socketChannel.config();
        channelConfig.setRecvByteBufAllocator(new FixedRecvByteBufAllocator(MIN_BUFFER_SIZE));
        socketChannel.pipeline().addLast(new JsonDecoder(), new JsonEncoder(), new ChunkedWriteHandler());
    }
}
