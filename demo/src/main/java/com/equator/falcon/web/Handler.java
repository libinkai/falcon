package com.equator.falcon.web;

import java.lang.reflect.Method;

/**
 * 封装 Action
 * @Author: Equator
 * @Date: 2020/2/12 8:47
 **/

public class Handler {
    private Class<?> controllerClass;

    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
