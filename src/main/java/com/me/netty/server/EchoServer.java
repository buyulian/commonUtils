package com.me.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author buyulian
 * @date 2019/12/10
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        // 创建 EventLoopGroup
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            // 创建 ServerBootstrap
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    // 指定使用 NIO 的传输 Channel
                    .channel(NioServerSocketChannel.class)
                    // 设置 socket 地址使用所选的端口
                    .localAddress(new InetSocketAddress(port))
                    // 添加 EchoServerHandler 到 Channel 的 ChannelPipeline
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            // 绑定的服务器;sync 等待服务器关闭
            ChannelFuture future = bootstrap.bind().sync();
            System.out.println(EchoServer.class.getName() + " started and listen on " + future.channel().localAddress());
            // 关闭 channel 和 块，直到它被关闭
            future.channel().closeFuture().sync();
        } finally {
            // 关闭 EventLoopGroup，释放所有资源。
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 4567;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }
        // 设计端口、启动服务器
        new EchoServer(port).start();
    }
}
