package com.equator.demo.service;


import com.equator.falcon.annotation.Service;

import java.util.Date;

/**
 * @Author: Equator
 * @Date: 2020/2/12 17:32
 **/

@Service
public class TestService {
    public Date doSomeThing() {
        System.out.println("一些业务操作...");
        return new Date();
    }
}
