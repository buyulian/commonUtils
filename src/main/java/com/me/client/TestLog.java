package com.me.client;

import com.me.json.JsonStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLog {
    static Logger logger= LoggerFactory.getLogger(TestLog.class);
    public static void display(){
        String json="";
        String rs= JsonStringUtil.jsonToClass(json);
        System.out.println(rs);
    }
}
