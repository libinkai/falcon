package com.equator.falcon.aop;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理链
 * TODO 搞明白这里
 *
 * @Author: Equator
 * @Date: 2020/2/12 11:38
 **/

public class ProxyChain {
    /**
     * 目标类
     */
    private final Class<?> targetClass;
    /**
     * 目标对象
     */
    private final Object targetObject;
    /**
     * 目标方法
     */
    private final Method targetMethod;
    /**
     * 方法代理
     */
    private final MethodProxy methodProxy;
    /**
     * 方法参数
     */
    private final Object[] methodParams;

    private final List<Proxy> proxyList;
    private int proxyIndex = 0;

    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Object doProxyChain() throws Throwable {
        Object result = null;
        if (proxyIndex < proxyList.size()) {
            result = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            // invoke方法调用的对象没有增强过，invokeSuper方法调用的对象已经是增强了的，所以会再走一遍 MyMethodInterceptor的 interceptor方法，如果是个拦截器链条，就会重新在走一次拦截器链
            result = methodProxy.invokeSuper(targetObject, methodParams);
        }
        return result;
    }
}
