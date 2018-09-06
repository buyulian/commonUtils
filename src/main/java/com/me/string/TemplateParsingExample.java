package com.me.string;
import java.text.MessageFormat;

public class TemplateParsingExample {
    public void stringParsing(){
        System.out.println(MessageFormat.format("我是{0},我来自{1},今年{2}岁", "中国人", "北京", "22"));
    }
}
