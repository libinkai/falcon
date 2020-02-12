package com.equator.falcon.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 编码与解码
 *
 * @Author: Equator
 * @Date: 2020/2/12 9:48
 **/

public class CodecUtil {
    public static String encodeUrl(String source) {
        String target = null;
        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return target;
    }

    public static String decodeUrl(String source) {
        String target = null;
        try {
            target = URLDecoder.decode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return target;
    }

    public static Map<String, String> getParamsFromRequestBody(String body) {
        Map<String, String> map = new HashMap<>();
        String[] paramKeyValues = body.split("&");
        for (String paramKeyValue : paramKeyValues) {
            String[] arr = paramKeyValue.split("=");
            if (ArrayUtil.isNotEmpty(arr) && arr.length == 2) {
                map.put(arr[0], arr[1]);
            }
        }
        return map;
    }
}
