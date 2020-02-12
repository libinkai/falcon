package com.equator.falcon.web;

import com.equator.falcon.util.JsonUtil;

/**
 * @Author: Equator
 * @Date: 2020/2/12 17:37
 **/

public class Response {
    private int code;
    private String msg;
    private Object data;

    public Response(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static JsonData response(int code, String msg, Object data) {
        return new JsonData(new Response(code, msg, data));
    }
}
