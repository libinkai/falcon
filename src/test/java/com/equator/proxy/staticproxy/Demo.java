package com.equator.proxy.staticproxy;

/**
 * @Author: Equator
 * @Date: 2020/2/12 16:06
 **/

public class Demo {
    public static void main(String[] args) {
        ElephantAction elephantAction = new ElephantActionImpl();
        ElephantActionProxy elephantActionProxy = new ElephantActionProxy(elephantAction);
        elephantActionProxy.action();
        System.out.println("-----------------");
        DesireAction desireAction = new DesireActionImpl();
        DesireActionProxy desireActionProxy = new DesireActionProxy(desireAction);
        desireActionProxy.action();
        System.out.println("-----------------");
    }
}
