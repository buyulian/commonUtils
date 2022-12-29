package com.me.javaa;

import com.alibaba.fastjson.JSONObject;
import com.me.file.FileIo;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertUtils {

    public static Pattern pattern = Pattern.compile("private\\s+\\w+\\s+(\\w+)\\s*;");

    public static void javaToJson(String filePath) {
        String content = FileIo.readFile(new File(filePath));
        Matcher m = pattern.matcher(content);
        JSONObject jsonObject = new JSONObject();
        while (m.find()) {
            String key = m.group(1);
            jsonObject.put(key, "");
        }
        System.out.println(jsonObject.toJSONString());
    }

}
