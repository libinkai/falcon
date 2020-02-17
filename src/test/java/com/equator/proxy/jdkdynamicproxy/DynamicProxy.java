package com.equator.proxy.jdkdynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 * @Author: Equator
 * @Date: 2020/2/12 16:18
 **/

public class DynamicProxy implements InvocationHandler {
    // 目标对象
    private Object target;

    public DynamicProxy(Object target) {
        this.target = target;
    }

    /**
     * 定义代理的逻辑（invoke 方法不需要自己调用）
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target, args);
        after();
        return result;
    }

    /**
     * 动态创建一个代理对象
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy() {
        // 类加载器 类的接口 代理类（实现了InvocationHandler接口）
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    private void before() {
        System.out.println("打开冰箱门");
    }

    private void after() {
        System.out.println("关闭冰箱门");
    }
}
