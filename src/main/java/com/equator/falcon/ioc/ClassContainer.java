package com.equator.falcon.ioc;

import com.equator.falcon.annotation.Controller;
import com.equator.falcon.annotation.Service;
import com.equator.falcon.configuration.ConfigurationConstant;
import com.equator.falcon.configuration.ConfigurationFactory;
import com.equator.falcon.util.ClassLoaderHelper;

import java.lang.annotation.Annotation;
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
     *
     * @return
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> set = new HashSet<>();
        set.addAll(getControllerClassSet());
        set.addAll(getServiceClassSet());
        return set;
    }

    /**
     * 获取某父类（接口）的子类（实现类）
     *
     * @param superClass
     * @return
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> set = new HashSet<>();
        for (Class<?> cls : classSet) {
            // class1.isAssignableFrom(class2)，判断class2是不是class1的子类或者子接口
            if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
                set.add(cls);
            }
        }
        return set;
    }

    /**
     * 获取带有某注解的类
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnotationType(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> set = new HashSet<>();
        for (Class<?> cls : classSet) {
            if (cls.isAnnotationPresent(annotationClass)) {
                set.add(cls);
            }
        }
        return set;
    }
}
