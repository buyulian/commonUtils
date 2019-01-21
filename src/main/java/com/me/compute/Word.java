package com.me.compute;

/**
 * Created with IntelliJ IDEA.
 * User: liujiacun
 * Date: 2019/1/18
 * Time: 13:04
 * Description: No Description
 */
public class Word {
    private Object data;
    private Type type;

    public Word(Object data, Type type) {
        this.data = data;
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type{
        NUMBER,OPERATOR,FUNCTION,COMMA;
    }
}
