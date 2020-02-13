package com.equator.falcon.web;

import com.equator.falcon.annotation.Action;
import com.equator.falcon.ioc.ClassContainer;
import com.equator.falcon.util.ArrayUtil;
import com.equator.falcon.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Equator
 * @Date: 2020/2/12 8:49
 **/

public final class HandlerMapping {
    private static Logger logger = LoggerFactory.getLogger(HandlerMapping.class);
    private static Map<Request, Handler> requestHandlerMap = new HashMap<>();

    static {
        logger.debug("HandlerMapping init ...");
        Set<Class<?>> controllerClassSet = ClassContainer.getControllerClassSet();
        if (CollectionUtil.isNotEmpty(controllerClassSet)) {
            for (Class<?> controllerClass : controllerClassSet) {
                Method[] methods = controllerClass.getDeclaredMethods();
                // 遍历带有 Action 注解的方法
                if (ArrayUtil.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        logger.debug("配置 method {}", method.getName());
                        if (method.isAnnotationPresent(Action.class)) {
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            // 验证mapping规则
                            logger.debug("mapping {} match {}", mapping, mapping.matches("(\\S+):(\\S+)"));
                            if (mapping.matches("(\\S+):(\\S+)")) {
                                String[] mapAsArray = mapping.split(":");
                                String requestMethod = mapAsArray[0];
                                String requestPath = mapAsArray[1];
                                logger.debug("requestMethod {}, requestPath {}", requestMethod, requestPath);
                                Request request = new Request(requestMethod, requestPath);
                                Handler handler = new Handler(controllerClass, method);
                                requestHandlerMap.put(request, handler);
                                logger.debug("已添加 request：{}，handler：{}", request, handler);
                            }
                        }
                    }
                }
            }
        }
    }

    public static Handler getHandler(String requestMethod, String requestPath) {
        logger.debug("requestMethod {}, requestPath {}", requestMethod, requestPath);
        Request request = new Request(requestMethod, requestPath);
        logger.debug("requestHandlerMap {}",requestHandlerMap.size());
        for (Map.Entry<Request, Handler> entry : requestHandlerMap.entrySet()) {
            logger.debug("entry.getKey().equals(request) {}",entry.getKey().equals(request));
        }
        return requestHandlerMap.get(request);
    }
}
