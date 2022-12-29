package com.me.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/**
 * @author buyulian
 * @date 2019/12/10
 */
public class EchoClient {

    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            // 创建 Bootstrap
            Bootstrap bootstrap = new Bootstrap();
            // 指定EventLoopGroup来处理客户端事件。由于我们使用NIO传输，所以用到了 NioEventLoopGroup 的实现
            bootstrap.group(group)
                    // 使用的channel类型是一个用于NIO传输
                    .channel(NioSocketChannel.class)
                    // 设置服务器的InetSocketAddr
                    .remoteAddress(new InetSocketAddress(host, port))
                    // 当建立一个连接和一个新的通道时。创建添加到EchoClientHandler实例到 channel pipeline
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new EchoClientHandler());
                        }
                    });

            // 连接到远程；等待连接完成
            ChannelFuture future = bootstrap.connect().sync();

            // 阻塞到远程; 等待连接完成
            future.channel().closeFuture().sync();

        } finally {
            // 关闭线程池和释放所有资源
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {

        final String host = "127.0.0.1";
        final int port = 4567;
        for (int i = 0; i < 100; i++) {
            new Thread(()->{

                try {

                    new EchoClient(host, port).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
