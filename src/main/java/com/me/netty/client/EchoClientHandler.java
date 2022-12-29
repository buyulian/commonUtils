package com.me.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author buyulian
 * @date 2019/12/10
 * @Sharable 标记这个类的实例可以在channel里共享
 */
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 当被通知该 channel 是活动的时候就发送信息
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!"+System.nanoTime(), CharsetUtil.UTF_8));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        // 记录接收到的消息
        System.out.println("Client received: "+System.nanoTime() + byteBuf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 记录日志错误并关闭 channel
        cause.printStackTrace();
        ctx.close();
    }
}
