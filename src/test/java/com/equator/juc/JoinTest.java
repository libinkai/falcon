package com.equator.juc;

import org.junit.Test;

/**
 * t.join() 用来让当前线程等待t线程执行完毕，示例当前线程是main线程
 *
 * @Author: Equator
 * @Date: 2020/2/13 10:14
 **/

public class JoinTest {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程1");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程2");
            }
        });

        thread1.start();
        // 34 与 35 行互换，效果不一样
        thread1.join();
        thread2.start();
        thread2.join();
        System.out.println("全部线程工作完毕");
    }
}
