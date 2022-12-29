package com.me.json;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.JavaBeanInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class FastJsonStrengthen {

    public static void addTypeInstance(Class<?> clazz, BiFunction biFunction) {
        ParserConfig parserConfig = ParserConfig.getGlobalInstance();
        List<FieldInfo> fieldList = new ArrayList<>();
        for (Class<?> currentClass = clazz; currentClass != null; currentClass = currentClass.getSuperclass()) {
            Field[] fields = currentClass.getDeclaredFields();

            try {
                Class<JavaBeanInfo> javaBeanInfoClass = JavaBeanInfo.class;
                Method[] methods = javaBeanInfoClass.getDeclaredMethods();
                Method computeFields = null;
                for (Method method : methods) {
                    if (method.getName().equals("computeFields")) {
                        computeFields = method;
                    }
                }

                computeFields.setAccessible(true);
                computeFields.invoke(null, clazz, clazz, parserConfig.propertyNamingStrategy, fieldList, fields);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        JavaBeanInfo javaBeanInfo = new JavaBeanInfo(clazz, clazz, null, null, null, null, null, fieldList);

        JavaBeanDeserializer deserializer = new JavaBeanDeserializer(parserConfig, javaBeanInfo){
            @Override
            public Object createInstance(DefaultJSONParser parser, Type type) {
                return biFunction.apply(parser, type);
            }
        };
        parserConfig.putDeserializer(clazz,
                deserializer);
    }

}
