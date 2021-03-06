package com.equator.falcon.ioc;

import com.equator.falcon.util.ArrayUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类加载器
 *
 * @Author: Equator
 * @Date: 2020/2/11 23:31
 **/

public class ClassLoaderHelper {
    private static Logger logger = LoggerFactory.getLogger(ClassLoaderHelper.class);

    /**
     * 获取类加载器
     *
     * @return
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     *
     * @param className
     * @param isInit
     * @return
     */
    public static Class<?> loadClass(String className, boolean isInit) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className, isInit, getClassLoader());
        } catch (ClassNotFoundException e) {
            logger.error("failed to loadClass {}", className);
            // e.printStackTrace();
        }
        return cls;
    }

    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();
        try {
            // 注意是\\. 而不是.
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replaceAll("\\.", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        String packagePath = url.getPath().replaceAll("%20", " ");
                        logger.debug("packagePath : {}", packagePath);
                        appendClass(classSet, packageName, packagePath);
                    } else if ("jar".equals(protocol)) {
                        logger.debug("load jar ...");
                        JarURLConnection connection = (JarURLConnection) url.openConnection();
                        if (connection != null) {
                            JarFile jarFile = connection.getJarFile();
                            if (jarFile != null) {
                                Enumeration<JarEntry> entries = jarFile.entries();
                                while (entries.hasMoreElements()) {
                                    JarEntry entry = entries.nextElement();
                                    String entryName = entry.getName();
                                    if (entryName.endsWith(".class")) {
                                        String className = entryName.substring(0, entryName.lastIndexOf(".")).replaceAll("/", ".");
                                        appendClass(classSet, className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classSet;

    }

    /**
     * 递归加载Class文件
     *
     * @param classSet
     * @param packageName
     * @param packagePath
     */
    public static void appendClass(Set<Class<?>> classSet, String packageName, String packagePath) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile() && file.getName().endsWith(".class") || file.isDirectory();
            }
        });
        if (ArrayUtil.isNotEmpty(files)) {
            for (File file : files) {
                String fileName = file.getName();
                if (file.isFile()) {
                    String className = fileName.substring(0, fileName.lastIndexOf("."));
                    if (StringUtils.isNotEmpty(packageName)) {
                        className = StringUtils.join(packageName, ".", className);
                    }
                    appendClass(classSet, className);
                } else {
                    logger.debug("load dir : {}", fileName);
                    String subPackagePath = fileName;
                    if (StringUtils.isNotEmpty(packagePath)) {
                        subPackagePath = StringUtils.join(packagePath, subPackagePath);
                    }
                    String subPackageName = fileName;
                    if (StringUtils.isNotEmpty(packageName)) {
                        if (!"".equals(packageName)) {
                            subPackageName = StringUtils.join(packageName, ".", subPackageName);
                        }
                    }
                    appendClass(classSet, subPackageName, subPackagePath);
                }
            }
        }
    }

    /**
     * 通过反射，加载全路径表示的Class文件
     *
     * @param classSet
     * @param className
     */
    public static void appendClass(Set<Class<?>> classSet, String className) {
        logger.debug("load class : {}", className);
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }
}
