package com.equator.juc;

import java.util.concurrent.Exchanger;

/**
 * exchange 为交换内存之后的返回值
 * @Author: Equator
 * @Date: 2020/2/13 11:37
 **/

public class ExchangerTest {
    private static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String content = "I love u";
                try {
                    System.out.println("boy say "+exchanger.exchange(content));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String content = "me too";
                try {
                    System.out.println("girl say "+exchanger.exchange(content));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
