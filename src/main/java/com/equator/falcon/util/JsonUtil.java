package com.equator.falcon.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author: Equator
 * @Date: 2020/2/12 9:57
 **/

public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> String toJson(T obj) {
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public static <T> T parseJson(String jsonString, Class<T> type) {
        T obj = null;
        try {
            obj = objectMapper.readValue(jsonString, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
