package com.equator.demo.controller;

import com.equator.demo.model.POJO;
import com.equator.demo.service.TestService;
import com.equator.falcon.annotation.Action;
import com.equator.falcon.annotation.Controller;
import com.equator.falcon.annotation.DI;
import com.equator.falcon.web.JsonData;
import com.equator.falcon.web.Response;

import java.util.Date;

/**
 * @Author: Equator
 * @Date: 2020/2/12 17:32
 **/

@Controller
public class TestController {
    @DI
    private TestService testService;

    @Action("get:/test/ping")
    public JsonData ping() {
        System.out.println("entry ...");
        POJO pojo = new POJO();
        testService.doSomeThing();
        pojo.setBirthday(new Date());
        pojo.setName("equator");
        return Response.response(200, "pong", pojo);
    }
}
