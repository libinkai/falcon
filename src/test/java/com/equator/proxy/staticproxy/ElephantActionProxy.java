package com.equator.proxy.staticproxy;

/**
 * @Author: Equator
 * @Date: 2020/2/12 16:04
 **/

public class ElephantActionProxy implements ElephantAction {
    private ElephantAction elephantAction;

    public ElephantActionProxy(ElephantAction elephantAction) {
        this.elephantAction = elephantAction;
    }

    @Override
    public void action() {
        System.out.println("打开冰箱门");
        elephantAction.action();
        System.out.println("关闭冰箱门");
    }
}
