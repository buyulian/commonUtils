package com.me.string;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author buyulian
 * @date 2020/3/28
 */
public class JsonToCode {

    public String jsonStrToJsonCode(String str) {
        Object obj = JSON.parse(str);

        return getCodeByValue(obj, "root").value;
    }

    private class Result{
        public String key;
        public String value;

        public Result() {
        }

        public Result(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private Result getCodeByValue(Object obj, String key) {

        if (obj == null) {
            return new Result(null, null);
        }

        StringBuilder sb = new StringBuilder();

        Result result = new Result();
        if (obj instanceof JSONObject) {
            String objName = String.format("%sObj",key);
            result.key = objName;
            sb.append(String.format("JSONObject %s = new JSONObject();\n",objName));
            JSONObject jsonObject = (JSONObject) obj;
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                Object value = entry.getValue();
                Result result1 = getCodeByValue(value, entry.getKey());

                if (result1.key != null) {
                    sb.append(result1.value)
                            .append(String.format("%s.put(\"%s\", %s);\n",objName,entry.getKey(),result1.key));
                } else {
                    sb.append(String.format("%s.put(\"%s\", %s);\n",objName,entry.getKey(),result1.value));
                }

            }

        } else if (obj instanceof JSONArray){
            String objName = String.format("%sArr",key);
            result.key = objName;
            sb.append(String.format("JSONArray %s = new JSONArray();\n",objName));
            JSONArray jsonArray = (JSONArray) obj;

            int i = 0;
            for (Object objItem : jsonArray) {
                Result result1 = getCodeByValue(objItem, objName+"Item"+String.valueOf(i));
                i++;

                if (result1.key != null) {
                    sb.append(result1.value)
                            .append(String.format("%s.add(%s);\n",objName,result1.key));
                } else {
                    sb.append(String.format("%s.add(%s);\n",objName,result1.value));
                }
            }


        } else {
            sb.append("\"").append(obj.toString()).append("\"");
        }

        result.value = sb.toString();
        return result;
    }
}
