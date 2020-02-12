package com.equator.proxy.cglidynamicproxy;

/**
 * @Author: Equator
 * @Date: 2020/2/12 16:57
 **/

public class Demo {
    public static void main(String[] args) {
        PrideAction prideAction = new PrideAction();
        DynamicProxy dynamicProxy = new DynamicProxy(prideAction);
        PrideAction proxy = dynamicProxy.getProxy();
        proxy.action();
    }
}
