package com.equator.falcon.aop;

import com.equator.falcon.annotation.Aspect;
import com.equator.falcon.ioc.BeanContainer;
import com.equator.falcon.ioc.ClassContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @Author: Equator
 * @Date: 2020/2/12 12:24
 **/

public class AopHelper {
    private static Logger logger = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            // 代理类 -> 被代理类集合
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            // 被代理类 -> 代理类列表（链式调用）
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            logger.debug("加载 aopHelper, proxyMap size :{}, targetMap size :{}", proxyMap.size(), targetMap.size());
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                // 获取代理对象并保存（将原来的实例替换为代理类实例了）
                Object proxy = ProxyFacroty.createProxy(targetClass, proxyList);
                BeanContainer.setBean(targetClass, proxy);
                logger.debug("add aop {} ...", targetClass.getName());
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
        Set<Class<?>> proxyClassSet = ClassContainer.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            // 使用AOP时，定义的切面需要有Aspect注解以及继承AspectProxy，选择性得实现AspectProxy得模板方法
            // 如果是按照正常的使用流程来说，AspectProxy的子类都会有Aspect注解，这里看作是双重检查吧
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                // 这里面的AOP是代理了被某个注解标记了的所有类（如Service注解下的UserService类，OrderService类，都会被代理）
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
