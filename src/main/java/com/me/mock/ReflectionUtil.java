package com.me.mock;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

public class ReflectionUtil {
    /***
     * 获取私有成员变量的值
     *
     */
    public static Object getFields(Object object, String fieldName)
            throws IllegalAccessException, NoSuchFieldException {

        String[] fields=fieldName.split("\\.");
        Object toObject=object;
        Field field = object.getClass().getDeclaredField(fields[0]);
        for(int i=1;i<fields.length;i++){
            field = toObject.getClass().getDeclaredField(fields[i]);
            field.setAccessible(true);
            toObject=field.get(toObject);
        }
        return field;
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
    public static void setSuperField(Object object, String fieldName, Object value)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

        Class curClass = object.getClass();
        Field field = null;

        while (curClass != null){
            boolean isExist = true;
            try {
                field = curClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                isExist = false;
            }
            if(isExist){
                break;
            }
            curClass = curClass.getSuperclass();
        }

        if(field == null){
            throw new NoSuchFieldException();
        }

        field.setAccessible(true);
        field.set(object, value);
    }
    /***
     * 设置私有成员变量的值
     *
     */
    public static void setSuperFieldAll(Object object, String fieldName, Object value,int depth)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

        Class curClass;
        Field field = null;

        boolean existOne = false;

        for(curClass = object.getClass(); curClass !=null&&depth>0; curClass = curClass.getSuperclass(),depth--){
            boolean isExist = true;
            try {
                field = curClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                isExist = false;
            }
            if(!isExist){
                continue;
            }

            existOne = true;
            field.setAccessible(true);
            field.set(object, value);
        }

        if(!existOne){
            throw new NoSuchFieldException(fieldName);
        }
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


    public static void initField(Object billDto) {
        Class<?> aClass = billDto.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            try {
                field.setAccessible(true);
                Object objSub = field.get(billDto);
                if (Objects.isNull(objSub)) {
                    Object value = null;
                    if (field.getType().equals(Integer.class)) {
                        value = new Integer(0);
                    } else if (field.getType().equals(String.class)){
                        value = "1";
                    } else if (field.getType().equals(String.class)){
                        value = "1";
                    } else if (field.getType().equals(Byte.class)){
                        value = (byte)2;
                    } else if (field.getType().equals(Long.class)){
                        value = 12349L;
                    } else if (field.getType().equals(Double.class)){
                        value = 1.23789;
                    } else if (field.getType().equals(BigDecimal.class)){
                        value = new BigDecimal("1.784323");
                    } else if (field.getType().equals(Date.class)){
                        value = new Date();
                    } else if (field.getType().equals(List.class)) {
                        value = new LinkedList<>();
                    } else if (field.getType().equals(Map.class)) {
                        value = new HashMap<>();
                    }
                    field.set(billDto,value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
