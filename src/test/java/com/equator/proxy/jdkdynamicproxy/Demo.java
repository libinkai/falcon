package com.equator.proxy.jdkdynamicproxy;

import com.equator.proxy.staticproxy.DesireAction;
import com.equator.proxy.staticproxy.DesireActionImpl;
import com.equator.proxy.staticproxy.ElephantAction;
import com.equator.proxy.staticproxy.ElephantActionImpl;

import java.lang.reflect.Proxy;

/**
 * @Author: Equator
 * @Date: 2020/2/12 16:22
 **/

public class Demo {
    public static void main(String[] args) {
        ElephantActionImpl elephantAction = new ElephantActionImpl();
        DynamicProxy elephantDynamicProxy = new DynamicProxy(elephantAction);
        ElephantAction elephantActionProxy = elephantDynamicProxy.getProxy();
        elephantActionProxy.action();
        System.out.println("-----------------");

        DesireAction desireAction = new DesireActionImpl();
        DynamicProxy desireDynamicProxy = new DynamicProxy(desireAction);
        DesireAction desireActionProxy = desireDynamicProxy.getProxy();
        desireActionProxy.action();
        System.out.println("-----------------");
    }
}
