package com.zgw.concurrency.dome;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 〈〉
 *
 * @author gw.Zeng
 * @create 2019/5/15
 * @since 1.0.0
 */
public class LockDomeTest {
    static Lock lock = new ReentrantLock();//重入锁
    public static void main(String[] args) {

        lock.lock();

        try {
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        lock.unlock();


    }


}
