package com.me.io;
import com.me.io.nio.Client;
import com.me.io.nio.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试方法
 * @author yangtao__anxpp.com
 * @version 1.0
 */
public class NioTest {

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
        Client.start();
    }
}
