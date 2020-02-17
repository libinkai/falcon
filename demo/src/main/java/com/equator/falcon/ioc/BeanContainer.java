package com.equator.falcon.ioc;

import com.equator.falcon.util.ReflectionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Equator
 * @Date: 2020/2/12 7:48
 **/

public class BeanContainer {
    private static Logger logger = LoggerFactory.getLogger(BeanContainer.class);

    private final static Map<Class<?>, Object> beanMap = new HashMap<>();

    static {
        Set<Class<?>> classSet = ClassContainer.getBeanClassSet();
        for (Class<?> cls : classSet) {
            Object obj = ReflectionHelper.newInstance(cls);
            beanMap.put(cls, obj);
        }
        logger.debug("BeanContainer 初始化...");
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return beanMap;
    }

    public static <T> T getBean(Class<?> cls) {
        if (!beanMap.containsKey(cls)) {
            throw new RuntimeException("Failed to getBean");
        }
        return (T) beanMap.get(cls);
    }

    public static void setBean(Class<?> cls, Object obj) {
        beanMap.put(cls, obj);
    }
}
