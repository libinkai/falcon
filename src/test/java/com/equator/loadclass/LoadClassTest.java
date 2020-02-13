package com.equator.loadclass;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Equator
 * @Date: 2020/2/12 23:42
 **/

public class LoadClassTest {
    @Test
    public void test1() {
        String packageName = "com.equator.loadclass";
        String referencePath = packageName.replaceAll("\\.", "/");
        try {
            Enumeration<URL> urlEnumeration = Thread.currentThread().getContextClassLoader().getResources(referencePath);
            while (urlEnumeration.hasMoreElements()) {
                URL url = urlEnumeration.nextElement();
                System.out.println(url.getPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(referencePath);
        File file = new File(referencePath);
        System.out.println(file.exists());
        System.out.println(file.getAbsolutePath());
    }

    @Test
    public void test2() {
        String packageName = "src/test/java/com/equator";
        File file = new File(packageName);
        System.out.println(file.exists());
        File[] files = file.listFiles();
        for (File f : files) {
            System.out.println(f.getName());
        }
    }

    @Test
    public void loadClass() {
        List<File> fileList = new LinkedList<>();
        String packageName = "com.equator";
        String referencePath = packageName.replaceAll("\\.", "/");
        try {
            Enumeration<URL> urlEnumeration = Thread.currentThread().getContextClassLoader().getResources(referencePath);
            while (urlEnumeration.hasMoreElements()) {
                URL url = urlEnumeration.nextElement();
                System.out.println(url.getPath());
                File file = new File(url.getPath());
                loadFile(fileList,file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (File file : fileList) {
            System.out.println(file.getName());
        }
    }

    public void loadFile(List<File> fileList, File file) {
        if (file.isFile()){
            fileList.add(file);
        }else {
            File[] files = file.listFiles();
            for (File f : files) {
                loadFile(fileList,f);
            }
        }
    }
}
