package com.equator.falcon.web;

import com.equator.falcon.aop.AopHelper;
import com.equator.falcon.ioc.IocHelper;
import com.equator.falcon.ioc.BeanContainer;
import com.equator.falcon.ioc.ClassContainer;
import com.equator.falcon.util.ClassLoaderHelper;

/**
 * 框架启动入口
 *
 * @Author: Equator
 * @Date: 2020/2/12 9:05
 **/

public class ComponentLoader {
    /**
     * 初始化基本类
     * AOP需要在IOC之前，因为需要通过AOP获取到代理对象，才可以进行依赖注入
     */
    public static void init() {
        Class<?>[] classList = {
                ClassContainer.class,
                BeanContainer.class,
                AopHelper.class,
                IocHelper.class,
                HandlerMapping.class
        };
        for (Class<?> cls : classList) {
            ClassLoaderHelper.loadClass(cls.getName(), true);
        }
    }
}
