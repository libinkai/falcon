package com.equator.falcon.aop;

import java.lang.reflect.Method;

/**
 * 模板方法设计模式
 *
 * @Author: Equator
 * @Date: 2020/2/12 12:00
 **/

public class AspectProxy implements Proxy {
    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();
        begin();
        try {
            if (intercept(cls, method, params)) {
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            error(cls, method, params);
            e.printStackTrace();
        } finally {
            end();
        }
        return result;
    }

    public void begin() {

    }

    public boolean intercept(Class<?> cls, Method method, Object[] params) {
        return true;
    }

    public void before(Class<?> cls, Method method, Object[] params) {

    }

    public void after(Class<?> cls, Method method, Object[] params) {

    }

    public void error(Class<?> cls, Method method, Object[] params) {

    }

    public void end() {

    }
}
