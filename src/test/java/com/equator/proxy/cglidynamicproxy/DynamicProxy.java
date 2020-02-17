package com.equator.proxy.cglidynamicproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: Equator
 * @Date: 2020/2/12 16:46
 **/

public class DynamicProxy implements MethodInterceptor {
    // 目标对象
    Object target;

    public DynamicProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object targetObject, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        // Object result = methodProxy.invoke(o, objects);
        // Object result = method.invoke(o, objects);
        Object result = methodProxy.invokeSuper(targetObject, objects);
        after();
        return result;
    }

    /**
     * 创建代理类
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy() {
        // 目标类 代理类（实现了MethodInterceptor接口，即间接继承了Callable接口）
        return (T) Enhancer.create(target.getClass(), this);
    }

    private void before() {
        System.out.println("打开冰箱门");
    }

    private void after() {
        System.out.println("关闭冰箱门");
    }
}
