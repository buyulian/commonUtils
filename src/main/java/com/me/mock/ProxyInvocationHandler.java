package com.me.mock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyInvocationHandler implements InvocationHandler {
    private Object subject;

    public ProxyInvocationHandler(Object subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] args)
            throws Throwable {
        System.out.println("before rent house");

        Object rs = method.invoke(subject, args);

        System.out.println("after rent house");

        return rs;
    }

}