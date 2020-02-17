package com.equator.demo.aop;

import com.equator.falcon.annotation.Aspect;
import com.equator.falcon.annotation.Service;
import com.equator.falcon.aop.AspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 代理所有service的方法
 *
 * @Author: Equator
 * @Date: 2020/2/17 15:50
 **/

@Aspect(Service.class)
public class TestAspect extends AspectProxy {
    private static Logger logger = LoggerFactory.getLogger(TestAspect.class);

    @Override
    public void begin() {
        logger.debug("aop begin ...");
    }

    @Override
    public void end() {
        logger.debug("aop end ...");
    }
}
