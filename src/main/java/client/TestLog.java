package client;

import json.JsonStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLog {
    static Logger logger= LoggerFactory.getLogger(TestLog.class);
    public static void display(){
        String json="{\n" +
                "  \"seqTradeNo\": \"6f613d35e772457ca141f34713f6e9e7\",\n" +
                "  \"tradeType\": 0,\n" +
                "  \"slaveOrderNo\": \"12123123123\",\n" +
                "  \"tradeTime\": \"20180813180305\",\n" +
                "  \"tradeNo\": \"123123124123123\",\n" +
                "  \"status\": 2,\n" +
                "  \"masterOrderNo\": \"1532401091105\",\n" +
                "  \"merMark\": \"{}\",\n" +
                "  \"merId\": \"4567887654\",\n" +
                "  \"mark\": \"{“orderType”:” 20RA6301”,” bankCode”,”jbdzgswydfx”}\",\n" +
                "  \"batchId\": \"test12345678\",\n" +
                "  \"amount\": 1,\n" +
                "  \"time\": \"20180813180305\",\n" +
                "  \"clsDate\": \"20180707\",\n" +
                "  \"sysId\": \"22318136\",\n" +
                "  \"bizNo\": \"test\"\n" +
                "}";
        String rs= JsonStringUtil.jsonToClass(json);
        System.out.println(rs);
    }
}
