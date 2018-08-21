package json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Map;

public class JsonStringUtil {
    public static String jsonToClass(String json){
        JsonObject jsonObject=GsonUtil.toJsonObject(json);
        return jsonObject.entrySet().stream()
                .map(Map.Entry::getKey)
                .collect(StringBuilder::new, (sb,str)->{
                    sb.append("private String ")
                            .append(str)
                            .append(";\n\n");
                },(a,b)->{})
                .toString();
    }
}
