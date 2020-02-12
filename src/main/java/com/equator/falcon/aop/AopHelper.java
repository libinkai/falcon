package com.equator.falcon.aop;

import com.equator.falcon.annotation.Aspect;
import com.equator.falcon.ioc.BeanContainer;
import com.equator.falcon.ioc.ClassContainer;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @Author: Equator
 * @Date: 2020/2/12 12:24
 **/

public class AopHelper {
    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                // 获取代理对象并保存
                Object proxy = ProxyFacroty.createProxy(targetClass, proxyList);
                BeanContainer.setBean(targetClass, proxy);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Aspect注解中设置的注解类
     *
     * @param aspect
     * @return
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) {
        Set<Class<?>> targetClassSet = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        if (annotation != null && !annotation.equals(Aspect.class)) {
            targetClassSet.addAll(ClassContainer.getClassSetByAnnotationType(annotation));
        }
        return targetClassSet;
    }

    /**
     * 获取代理类以及其目标集合之间的映射关系
     *
     * @return
     */
    public static Map<Class<?>, Set<Class<?>>> createProxyMap() {
        Map<Class<?>, Set<Class<?>>> proxySetMap = new HashMap<>();
        Set<Class<?>> proxyClassSet = ClassContainer.getClassSetBySuper(Aspect.class);
        for (Class<?> proxyClass : proxyClassSet) {
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxySetMap.put(proxyClass, targetClassSet);
            }
        }
        return proxySetMap;
    }

    /**
     * 根据代理类以及其目标集合之间的映射关系，分析出目标类以及代理对象之间的映射关系
     *
     * @return
     */
    public static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxySetMap) throws IllegalAccessException, InstantiationException {
        Map<Class<?>, List<Proxy>> proxyListMap = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxySetMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (proxyListMap.containsKey(targetClass)) {
                    proxyListMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    proxyListMap.put(targetClass, proxyList);
                }
            }
        }
        return proxyListMap;
    }
}
