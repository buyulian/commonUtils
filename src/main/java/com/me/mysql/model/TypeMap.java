package com.me.mysql.model;

import java.util.HashMap;
import java.util.Map;

public class TypeMap{
    private static Map<String ,String > map=new HashMap<>(40);

    static {
        map.put("Integer","INTEGER");
        map.put("int","INTEGER");
        map.put("INTEGER","Integer");
        map.put("INT","Integer");

        map.put("String","VARCHAR");
        map.put("VARCHAR","String");

        map.put("Date","TIMESTAMP");
        map.put("TIMESTAMP","Date");

        map.put("DATETIME","Date");

        map.put("Double","DOUBLE");
        map.put("double","DOUBLE");
        map.put("DOUBLE","Double");

        map.put("Long","BIGINT");
        map.put("long","BIGINT");
        map.put("BIGINT","Long");

        map.put("Boolean","BIT");
        map.put("boolean","BIT");
        map.put("BIT","Boolean");

        map.put("Byte","TINYINT");
        map.put("byte","TINYINT");
        map.put("TINYINT","Byte");

        map.put("Short","SMALLINT");
        map.put("short","SMALLINT");
        map.put("SMALLINT","Short");
    }

    public static String get(String key){
        String rs=map.get(key);
        if(rs==null){
            System.out.println("未支持的类型 "+key+"，请往 typeMap 中添加类型映射");
        }
        return rs;
    }
    private TypeMap(){}
}
