package com.equator.falcon.web;

import java.util.Enumeration;
import java.util.Map;

/**
 * @Author: Equator
 * @Date: 2020/2/12 9:13
 **/

public class RequestParam {

    private Map<String, Object> paramMap;

    public RequestParam(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

}
