package com.equator.falcon.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author: Equator
 * @Date: 2020/2/11 23:08
 **/

public class PropertiesReader {
    public static Properties loadProperties(String filePath) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
