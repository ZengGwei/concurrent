package com.zgw.concurrency.Lock;

/**
 * 〈〉
 *
 * @author gw.Zeng
 * @create 2019/5/13
 * @since 1.0.0
 */
public class SynchronizedDemo implements Runnable {
    int x = 100;
    public static void main(String[] args) {
        SynchronizedDemo sd = new SynchronizedDemo();
        new Thread(() -> sd.m1("thread1")).start();
        new Thread(() -> sd.m2("thread2")).start();
        sd.m2("mainTread");
        System.out.println("Main x=" + sd.x);
    }

    public synchronized void m1(String name) {
        System.out.println(name+":m1-> entry");
        x = 1000;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("x= " + x);
        System.out.println(name+":m1-> exit");
    }

    public synchronized void m2(String name) {
        System.out.println(name+":m2 -> entry");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        x = 2000;
        System.out.println(name+":m2 -> exit");
    }

    @Override
    public void run() {
        System.out.println("run :");
        m2("run :");
    }
}
