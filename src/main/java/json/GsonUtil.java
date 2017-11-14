package json;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
 * 封装的GSON解析工具类，提供泛型参数
 */
public class GsonUtil {

    // 将对象编译成json
    public static String objectTojson(Object object) {
        Gson gson = new Gson();
        String result = gson.toJson(object);
        return result;
    }

    // 将Json数据解析成相应的映射对象
    public static <T> T jsonToObject(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return result;
    }

    // 将Json数组解析成相应的映射对象列表
    public static <T> List<T> jsonArrayToList(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        List<T> result = gson.fromJson(jsonData, new TypeToken<List<T>>(){}.getType());
        return result;
    }

    public static <T> LinkedList<T> jsonArrayToListWithType(String json, Class<T> clazz)
    {
        Type type = new TypeToken<ArrayList<JsonObject>>(){}.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        LinkedList<T> linkedList = new LinkedList<T>();
        for (JsonObject jsonObject : jsonObjects)
        {
            linkedList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return linkedList;
    }
}