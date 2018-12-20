package com.me.encryption;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.FileInputStream;
import java.nio.charset.Charset;

public class Encrypt {
    public static String getMd5(String fileName) throws Exception {
        String md5="";
        md5= DigestUtils.md5Hex(new FileInputStream(fileName));
        return md5;
    }

    public static String simpleEncrypt(String data,String key){
        byte[] bytes = data.getBytes(Charset.forName("UTF-8"));
        byte[] keyBytes = key.getBytes(Charset.forName("UTF-8"));
        for(int i=0;i<bytes.length;i++){
            bytes[i]=(byte)(bytes[i]^keyBytes[i%keyBytes.length]);
        }
        return new String(bytes);
    }

    public static void main(String[] args) {
        String data="123asdc";
        String key="qazxsw";
        String rs = simpleEncrypt(data, key);
        System.out.println(rs);
        String s = simpleEncrypt(rs, key);
        System.out.println(s);
        String s2 = simpleEncrypt("@SI\u0019 \u0013\u0012", key);
        System.out.println(s2);
    }

}
