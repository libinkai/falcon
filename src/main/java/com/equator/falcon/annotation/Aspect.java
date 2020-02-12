package com.equator.falcon.annotation;

import java.lang.annotation.*;

/**
 * @Author: Equator
 * @Date: 2020/2/12 11:36
 **/

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();
}
