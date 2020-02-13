package com.equator.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author: Equator
 * @Date: 2020/2/13 10:35
 **/

public class CyclicBarrierTest {
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程1");
                try {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    cyclicBarrier.await();
                    System.out.println("线程1 done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程2");
                try {
                    cyclicBarrier.await();
                    System.out.println("线程2 done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        System.out.println("main");
        Thread.sleep(5000);
        cyclicBarrier.await();
        System.out.println("all done");
    }
}
