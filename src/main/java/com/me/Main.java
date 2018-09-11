package com.me;

import com.me.client.TestLog;
import com.me.encryption.HashUtil;

public class Main {
    public static void main(String[] args) throws Exception {
        String path="E:\\programData\\mavenrepository\\com\\jcloud\\jss-sdk-java\\1.2.0-SNAPSHOT\\";
        System.out.println(HashUtil.getMd5(path+"jss-sdk-java-1.2.0-SNAPSHOT.jar"));
        System.out.println(HashUtil.getMd5(path+"jss-sdk-java-1.2.0-20170922.125606-2.jar"));
    }
}
