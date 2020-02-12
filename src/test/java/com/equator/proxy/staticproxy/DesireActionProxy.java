package com.equator.proxy.staticproxy;

/**
 * @Author: Equator
 * @Date: 2020/2/12 16:11
 **/

public class DesireActionProxy implements DesireAction {
    private DesireAction desireAction;

    public DesireActionProxy(DesireAction desireAction) {
        this.desireAction = desireAction;
    }

    @Override
    public void action() {
        System.out.println("打开冰箱门");
        desireAction.action();
        System.out.println("关闭冰箱门");
    }
}
