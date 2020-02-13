package com.equator.juc;

import java.util.concurrent.Semaphore;

/**
 * @Author: Equator
 * @Date: 2020/2/13 11:31
 **/

public class SemaphoreTest {
    private static Semaphore semaphore = new Semaphore(3);

    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + "acquire");
                        semaphore.acquire();
                        Thread.sleep(5000);
                        System.out.println(Thread.currentThread().getName() + "done");
                        semaphore.release();
                        System.out.println(Thread.currentThread().getName() + "release");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "thread-" + i).start();
        }
    }
}
