package com.equator.falcon.web;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @Author: Equator
 * @Date: 2020/2/12 17:37
 **/

@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
