package com.me.mock;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtil {
    /***
     * 获取私有成员变量的值
     *
     */
    public static Object getValue(Object object, String fieldName)
            throws IllegalAccessException, NoSuchFieldException {

        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true); // 参数值为true，禁止访问控制检查

        return field.get(object);
    }

    /***
     * 设置私有成员变量的值
     *
     */
    public static void setField(Object object, String fieldName, Object value)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    /***
     * 设置私有成员变量的值
     *
     */
    public static void setFields(Object object, String fieldName, Object value)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

        String[] fields=fieldName.split("\\.");
        Object toObject=object;
        Field field = object.getClass().getDeclaredField(fields[0]);
        for(int i=1;i<fields.length;i++){
            field = toObject.getClass().getDeclaredField(fields[i]);
            field.setAccessible(true);
            toObject=field.get(toObject);
        }
        field.setAccessible(true);
        field.set(toObject, value);
    }

    /***
     * 访问私有方法
     *
     */
    public static Object callMethod(Object object, String methodName, Class[] classes, Object[] objects)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {

        Method method = object.getClass().getDeclaredMethod(methodName, classes);
        method.setAccessible(true);
        return method.invoke(object, objects);
    }
}
