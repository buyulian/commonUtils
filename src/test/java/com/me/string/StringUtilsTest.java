package com.me.string;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * StringUtils Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十一月 14, 2017</pre>
 */
public class StringUtilsTest {

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
    public void testMatcherInclude() throws Exception {
//TODO: Test goes here...
        String content = "abcdefghjklmn";
        String rs = StringUtils.matcherInclude("bc", "kl", content);
        Assert.assertEquals("bcdefghjkl", rs);
    }

    /**
     * Method: matcherExclude(String prefix, String suffix, String content)
     */
    @Test
    public void testMatcherExclude() throws Exception {
//TODO: Test goes here...
        String content = "abcdefghjklmn";
        String rs = StringUtils.matcherExclude("bc", "kl", content);
        Assert.assertEquals("defghj", rs);
    }

} 
