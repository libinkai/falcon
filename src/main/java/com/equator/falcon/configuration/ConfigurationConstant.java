package com.equator.falcon.configuration;

/**
 * 保存了配置文件的KEY
 * interface里面的变量都是 public static final 属性的
 * @Author: Equator
 * @Date: 2020/2/11 23:00
 **/

public interface ConfigurationConstant {
    String CONFIG_FILE = "falcon.properties";
    String BASE_URL = "falcon.framework.base-url";
    String JDBC_DRIVER_CLASS_NAME = "falcon.jdbc.driver-class-name";
    String JDBC_URL = "falcon.jdbc.url";
    String JDBC_USER = "falcon.jdbc.username";
    String JDBC_PASSWORD = "falcon.jdbc.password";
}
