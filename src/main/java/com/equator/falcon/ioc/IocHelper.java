package com.equator.falcon.ioc;

import com.equator.falcon.annotation.DI;
import com.equator.falcon.util.ArrayUtil;
import com.equator.falcon.util.MapUtil;
import com.equator.falcon.util.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @Author: Equator
 * @Date: 2020/2/12 8:03
 **/

public class IocHelper {
    static {
        Map<Class<?>, Object> beanMap = BeanContainer.getBeanMap();
        if (MapUtil.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                // 获取 web 类 与 实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                // 获取 web 的成员变量
                Field[] fields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(fields)) {
                    for (Field field : fields) {
                        // 判断成员变量是否有DI注解
                        if (field.isAnnotationPresent(DI.class)) {
                            Class<?> beanFieldClass = field.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            // 通过反射设置成员变量的值
                            if (beanFieldInstance != null) {
                                ReflectionHelper.setField(beanInstance, field, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
