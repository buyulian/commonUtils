package com.me.mock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public class MockInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object object, Method method, Object[] args)
            throws Throwable {
        System.out.println("before rent house");



        System.out.println("after rent house");

        return 0;
    }

}