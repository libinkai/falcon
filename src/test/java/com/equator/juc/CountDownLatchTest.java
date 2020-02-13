package com.equator.juc;

import java.util.concurrent.CountDownLatch;

/**
 * latch 门闩
 * CountDownLatch功能与join一致
 *
 * @Author: Equator
 * @Date: 2020/2/13 10:24
 **/

public class CountDownLatchTest {
    private static CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程1");
                countDownLatch.countDown();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程2");
                countDownLatch.countDown();
            }
        }).start();
        countDownLatch.await();
        System.out.println("all done");
    }
}
