package com.me.io;
import com.me.io.bio.Client;
import com.me.io.bio.ServerNormal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
/**
 * 测试方法
 * @author yangtao__anxpp.com
 * @version 1.0
 */
public class BioTest {

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
            ServerNormal.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method: matcherExclude(String prefix, String suffix, String content)
     */
    @Test
    public void clientTest() throws Exception {
        while(true){
            //随机产生算术表达式
            String expression =String.valueOf(Math.random());
            Client.send(expression);
            try {
                Thread.currentThread().sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
