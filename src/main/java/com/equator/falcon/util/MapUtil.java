package com.equator.falcon.util;

import java.util.Map;

/**
 * @Author: Equator
 * @Date: 2020/2/12 8:11
 **/

public class MapUtil {
    public static boolean isNotEmpty(Map map) {
        return map != null && map.size() != 0;
    }
}
