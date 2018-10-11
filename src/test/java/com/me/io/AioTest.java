package com.me.io;

import com.me.io.aio.server.Server;
import com.me.io.nio.Client;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

public class AioTest {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: matcherInclude(String prefix, String suffix, String content)
     */
    @Test
    public void serverTest() throws Exception {
        try {
            Server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method: matcherExclude(String prefix, String suffix, String content)
     */
    @Test
    public void clientTest() throws Exception {
        //运行服务器
        Server.start();
        //避免客户端先于服务器启动前执行代码
        Thread.sleep(100);
        //运行客户端
        Client.start();
        System.out.println("请输入请求消息：");
        Scanner scanner = new Scanner(System.in);
        while(Client.sendMsg(scanner.nextLine()));
    }
}
