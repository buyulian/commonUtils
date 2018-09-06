package com.me.string;

import com.me.encryption.HashUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * StringUtils Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十一月 14, 2017</pre>
 */
public class HashUtilsTest {

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
    public void testMd5() throws Exception {
        System.out.println(HashUtil.getMd5("E:\\workcode\\clbs\\clbs-common\\target\\clbs-common-0.0.1-SNAPSHOT.jar"));
        System.out.println(HashUtil.getMd5("E:\\programData\\mavenrepository\\com\\jd\\clbs\\clbs-common\\0.0.1-SNAPSHOT\\clbs-common-0.0.1-SNAPSHOT.jar"));
    }

} 
