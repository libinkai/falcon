package com.equator.regex;

import org.junit.Test;

/**
 * @Author: Equator
 * @Date: 2020/2/13 7:12
 **/

public class RegexTest {
    @Test
    public void test1() {
        String path = "get:/test/ping";
        System.out.println(path.matches("(\\S+):(\\S+)"));
    }
}
