package com.me.string;

/**
 * Created by buyulian on 2017/8/16.
 */
public class NameUtils {
    public static String toClassName(String name){
        String[] split = name.split("_");
        StringBuilder sb=new StringBuilder();
        for(String str:split){
            sb.append(str.substring(0, 1).toUpperCase()).append(str.substring(1));
        }
        return sb.toString();
    }
    public static String methodNameToSqlName(String name){
        StringBuilder sb = new StringBuilder();
        for (Character c : name.toCharArray()) {
            if (Character.isUpperCase(c)) {
                sb.append("_").append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    public static String toMethodName(String name){
        String[] split = name.split("_");
        StringBuilder sb=new StringBuilder();
        int a=0;
        for(String str:split){
            if(a==0){
                a=1;
                sb.append(str);
                continue;
            }
            sb.append(str.substring(0, 1).toUpperCase()).append(str.substring(1));
        }
        return sb.toString();
    }

    public static String classNameToMethodName(String methodName){
        return methodName.substring(0,1).toLowerCase()+methodName.substring(1);
    }

    public static String methodNameToClassName(String methodName){
        return methodName.substring(0,1).toUpperCase()+methodName.substring(1);
    }
}
