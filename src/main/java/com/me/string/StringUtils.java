package com.me.string;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static String matcherInclude(String prefix,String suffix,String content){
        String regex=prefix+".*?"+suffix;
        Pattern p=Pattern.compile(regex);
        Matcher matcher=p.matcher(content);
        if(matcher.find()){
            return matcher.group();
        }
        return null;
    }
    public static String matcherExclude(String prefix,String suffix,String content){
        String regex= MessageFormat.format("(?<={0}).*(?={1})",prefix,suffix);
        Pattern p=Pattern.compile(regex);
        Matcher matcher=p.matcher(content);
        if(matcher.find()){
            return matcher.group();
        }
        return null;
    }
    public static String messageFormat(String pattern,String... parameter){
        StringBuilder sb=new StringBuilder();
        int pc=0;
        for(int i=0;i<pattern.length()-1;i++){
            char c=pattern.charAt(i);
            char cn=pattern.charAt(i+1);
            if(c=='\\'&&cn=='{'){
                sb.append('{');
                i++;
            }else if(c=='{'&&cn=='}'){
                sb.append(parameter[pc++]);
                i++;
            }else{
                sb.append(c);
            }
        }
        sb.append(pattern.charAt(pattern.length()-1));
        return sb.toString();
    }

    public static String setProperty(Class clazz){
        StringBuilder sb=new StringBuilder();
        Method[] methods = clazz.getMethods();
        Arrays.stream(methods)
                .map(Method::getName)
                .filter(s->s.startsWith("set"))
                .forEach(s->{
                    String name=clazz.getSimpleName();
                    sb.append(NameUtils.classNameToMethodName(name)).append(".").append(s).append("(null);\n");
                });
        return sb.toString();
    }
}
