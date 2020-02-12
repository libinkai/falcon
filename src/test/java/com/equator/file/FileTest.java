package com.equator.file;

import org.junit.Test;

import java.io.File;

/**
 * @Author: Equator
 * @Date: 2020/2/12 18:37
 **/

public class FileTest {
    @Test
    public void test1() {
        // 绝对路径之前，有没有/无影响
        File file = new File("/D:/Equator.png");
        System.out.println(file.getPath());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.isFile());
    }

    @Test
    public void test2() {
        File file = new File("src/test/resources/test.properties");
        System.out.println(file.getPath());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.exists());
        System.out.println(FileTest.class.getResource("/").getPath());
        System.out.println(FileTest.class.getResource("*").getPath());
    }
}
