package com.me.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Date;

/**
 * @author buyulian
 * @date 2019/12/10
 * 标识这类的实例之间可以在 channel 里面共享
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        System.out.println(new Date()+"Server received " + in.toString(CharsetUtil.UTF_8));
        //将所接收的消息返回给发送者
        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ctx.write(in);
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                    .addListener(ChannelFutureListener.CLOSE);
            System.out.println(new Date()+" Server send " + in.toString(CharsetUtil.UTF_8));
        }).start();
    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) {
//        System.out.println("channelReadComplete");
//        //冲刷所有待审消息到远程节点。关闭通道后，操作完成
//        //将所接收的消息返回给发送者
//        new Thread(()->{
//            try {
//                System.out.println("channelReadComplete sleep start");
//                Thread.sleep(2000);
//                System.out.println("channelReadComplete sleep end");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
//                    .addListener(ChannelFutureListener.CLOSE);
//        }).start();
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
