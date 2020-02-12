package com.equator.falcon.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author: Equator
 * @Date: 2020/2/11 23:08
 **/

public class PropertiesReader {
    private static Logger logger = LoggerFactory.getLogger(PropertiesReader.class);

    public static Properties loadProperties(String filePath) {
        String path = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        Properties properties = new Properties();
        try {
            File file = new File(path + filePath);
            logger.debug("file path {}", file.getAbsoluteFile());
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
