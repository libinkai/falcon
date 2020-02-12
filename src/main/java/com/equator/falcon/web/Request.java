package com.equator.falcon.web;

/**
 * @Author: Equator
 * @Date: 2020/2/12 8:43
 **/

public class Request {
    private String requestMethod;

    private String requestPath;

    public Request(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }
}
