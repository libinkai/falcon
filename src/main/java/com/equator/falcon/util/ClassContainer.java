package com.equator.falcon.util;

import com.equator.falcon.annotation.Controller;
import com.equator.falcon.annotation.Service;
import com.equator.falcon.configuration.ConfigurationConstant;
import com.equator.falcon.configuration.ConfigurationFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Equator
 * @Date: 2020/2/12 7:35
 **/

public final class ClassContainer {
    private static Set<Class<?>> classSet = null;

    static {
        classSet = ClassLoaderHelper.getClassSet(ConfigurationFactory.getBaseUrl());
    }

    public static Set<Class<?>> getClassSet() {
        return classSet;
    }

    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> set = new HashSet<>();
        for (Class<?> cls : classSet) {
            if (cls.isAnnotationPresent(Service.class)) {
                set.add(cls);
            }
        }
        return set;
    }

    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> set = new HashSet<>();
        for (Class<?> cls : classSet) {
            if (cls.isAnnotationPresent(Controller.class)) {
                set.add(cls);
            }
        }
        return set;
    }

    /**
     * 获取被 controller、service 注解的类
     * @return
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> set = new HashSet<>();
        set.addAll(getControllerClassSet());
        set.addAll(getServiceClassSet());
        return set;
    }
}
