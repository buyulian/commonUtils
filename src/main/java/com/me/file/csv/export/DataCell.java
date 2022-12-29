package com.me.file.csv.export;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author buyulian
 * @date 2019/11/29
 */
public class DataCell {

    private String title;

    private String filedName;

    private String datePattern;

    private Map<String, Field> fieldMap = new HashMap<>();

    private Class clazz;

    private SixFunction<Object, String> getValueFunction;

    private String groupMainFieldName;

    private SixFunction<Object, String> groupMainFunction;


    public DataCell(String title, String filedName) {
        this.title = title;
        this.filedName = filedName;
    }

    public DataCell(String title, String filedName, String datePattern) {
        this.title = title;
        this.filedName = filedName;
        this.datePattern = datePattern;
    }

    public DataCell(String title, String filedName, String datePattern, String groupMainFieldName) {
        this.title = title;
        this.filedName = filedName;
        this.datePattern = datePattern;
        this.groupMainFieldName = groupMainFieldName;
    }

    public DataCell(String title, SixFunction<Object, String> getValueFunction) {
        this.title = title;
        this.getValueFunction = getValueFunction;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    public String getGroupMainFieldName() {
        return groupMainFieldName;
    }

    public void setGroupMainFieldName(String groupMainFieldName) {
        this.groupMainFieldName = groupMainFieldName;
    }

    public SixFunction<Object, String> getGroupMainFunction() {
        return groupMainFunction;
    }

    public void setGroupMainFunction(SixFunction<Object, String> groupMainFunction) {
        this.groupMainFunction = groupMainFunction;
    }

    public String getGroupMainValue(Object obj) throws NoSuchFieldException, IllegalAccessException {
        return getValueInner(obj, groupMainFieldName, groupMainFunction);

    }

    public boolean isNeedSpace() {
        return groupMainFieldName != null || groupMainFunction != null;
    }

    public String getValue(Object obj) throws NoSuchFieldException, IllegalAccessException {
        return getValueInner(obj, filedName, getValueFunction);
    }

    private String getValueInner(Object obj, String filedName, SixFunction<Object, String> getValueFunction) throws NoSuchFieldException, IllegalAccessException {
        if (obj instanceof Map) {
            Map map = (Map) obj;
            return map.get(filedName).toString();
        } else {
            return getValueFromObject(obj, filedName, getValueFunction);
        }

    }

    private String getValueFromObject(Object obj, String filedName, SixFunction<Object, String> getValueFunction) throws NoSuchFieldException, IllegalAccessException {

        if (getValueFunction != null) {
            return getValueFunction.apply(obj);
        }

        Field field = this.getField(obj, filedName);
        Object value = field.get(obj);

        String fieldValue = "";

        if (value != null) {
            if (value instanceof Date){
                String pattern = "yyyy-MM-dd HH:mm:ss";
                if (StringUtils.isNotEmpty(this.getDatePattern())){
                    pattern = this.getDatePattern();
                }
                fieldValue = DateFormatUtils.format((Date) value, pattern);
            } else {
                fieldValue = value.toString();
            }
        }

        return fieldValue;
    }

    private Class getClazz(Object obj) {
        if (clazz == null) {
            clazz = obj.getClass();
        }
        return clazz;
    }


    private Field getField(Object obj, String filedName) throws NoSuchFieldException {
        Field field = fieldMap.get(filedName);
        if (field == null) {
            Class aClass = getClazz(obj);
            field = aClass.getDeclaredField(filedName);
            field.setAccessible(true);
        }
        fieldMap.put(filedName,field);
        return field;
    }

}
