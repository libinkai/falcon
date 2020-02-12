package com.equator.falcon.web;

import com.equator.falcon.ioc.IocHelper;
import com.equator.falcon.util.BeanContainer;
import com.equator.falcon.util.ClassContainer;
import com.equator.falcon.util.ClassLoaderHelper;
import com.equator.falcon.web.HandlerMapping;

/**
 * 框架启动入口
 *
 * @Author: Equator
 * @Date: 2020/2/12 9:05
 **/

public class ComponentLoader {
    public static void init() {
        Class<?>[] classList = {
                ClassContainer.class,
                BeanContainer.class,
                IocHelper.class,
                HandlerMapping.class
        };
        for (Class<?> cls : classList) {
            ClassLoaderHelper.loadClass(cls.getName(), true);
        }
    }
}
