package com.equator.falcon.util;

import java.util.Collection;

/**
 * @Author: Equator
 * @Date: 2020/2/12 8:11
 **/

public class CollectionUtil {
    public static boolean isNotEmpty(Collection collection) {
        return collection != null && collection.size() != 0;
    }
}
