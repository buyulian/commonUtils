package com.me.mock;

import sun.security.jca.GetInstance;

import java.lang.reflect.Proxy;

/**
 * @author buyulian
 * @date 2019/3/16
 */
public class PowerMock {
    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> tClass){
        return (T) Proxy.newProxyInstance(tClass.getClassLoader()
                , new Class<?>[]{tClass}, new MockInvocationHandler());
    }
}
