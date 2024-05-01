package com.cyber.security.lab.network;

import com.cyber.security.lab.utils.Callback;
import com.cyber.security.lab.RequestBody;
import com.cyber.security.lab.ResponseBody;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.CompletableFuture;

public class NetworkImpl implements Network {
    private String address;
    private int port = Integer.MIN_VALUE;

    private SocketChannel channel;

    private NetworkImpl() {
    }

    @Override
    public void openConnection(Callback callback) {
        if (address == null || port == Integer.MIN_VALUE) {
            callback.onFailure("Address or port is not set");
            return;
        }
        new Thread(() -> {
            try (EventLoopGroup workerGroup = new NioEventLoopGroup()) {
                Bootstrap b = new Bootstrap();
                b.group(workerGroup)
                        .channel(NioSocketChannel.class)
                        .handler(new NetworkInitializer());
                var future = b.connect(address, port).sync();
                channel = (SocketChannel) future.channel();
                callback.onSuccess();
                future.channel().closeFuture().sync();
            } catch (Exception e) {
                callback.onFailure("Connection failed");
            }
        }).start();
    }

    @Override
    public CompletableFuture<ResponseBody> sendMessageAndGetFuture(RequestBody msg) {
        CompletableFuture<ResponseBody> future = new CompletableFuture<>();
        var handler = new JsonHandler(future, channel);
        channel.pipeline().addLast(handler);
        channel.writeAndFlush(msg);
        return future;
    }

    @Override
    public void closeConnection() {
        if (channel != null && channel.isActive()) {
            channel.close();
        }
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }
}
