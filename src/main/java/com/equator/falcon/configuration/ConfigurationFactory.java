package com.equator.falcon.configuration;

import java.util.Properties;

/**
 * @Author: Equator
 * @Date: 2020/2/11 23:08
 **/

public final class ConfigurationFactory {
    private final static Properties configuration = PropertiesReader.loadProperties(ConfigurationConstant.CONFIG_FILE);

    public static String getBaseUrl() {
        return configuration.getProperty(ConfigurationConstant.BASE_URL);
    }

    public static String getJdbcClassName() {
        return configuration.getProperty(ConfigurationConstant.JDBC_DRIVER_CLASS_NAME);
    }

    public static String getJdbcUrl() {
        return configuration.getProperty(ConfigurationConstant.JDBC_URL);
    }

    public static String getJdbcUser() {
        return configuration.getProperty(ConfigurationConstant.JDBC_USER);
    }

    public static String getJdbcPassword() {
        return configuration.getProperty(ConfigurationConstant.JDBC_PASSWORD);
    }
}
