package com.equator.falcon.web;

import com.equator.falcon.annotation.Action;
import com.equator.falcon.util.ArrayUtil;
import com.equator.falcon.ioc.ClassContainer;
import com.equator.falcon.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Equator
 * @Date: 2020/2/12 8:49
 **/

public final class HandlerMapping {
    private static Map<Request, Handler> requestHandlerMap = new HashMap<>();

    static {
        Set<Class<?>> controllerClassSet = ClassContainer.getControllerClassSet();
        if (CollectionUtil.isNotEmpty(controllerClassSet)) {
            for (Class<?> controllerClass : controllerClassSet) {
                Method[] methods = controllerClass.getDeclaredMethods();
                // 遍历带有 Action 注解的方法
                if (ArrayUtil.isNotEmpty(methods)) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Action.class)) {
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            // 验证mapping规则
                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] mapAsArray = mapping.split(":");
                                String requestMethod = mapAsArray[0];
                                String requestPath = mapAsArray[1];
                                Request request = new Request(requestMethod, requestPath);
                                Handler handler = new Handler(controllerClass, method);
                                requestHandlerMap.put(request, handler);
                            }
                        }
                    }
                }
            }
        }
    }

    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return requestHandlerMap.get(request);
    }
}
