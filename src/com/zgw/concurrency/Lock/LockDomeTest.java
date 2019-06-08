package com.zgw.concurrency.Lock;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 〈〉
 *Lock 比synchronized 更灵活，手动的显式的获取锁和释放锁
 * 1.非阻塞的获取锁
 * 2.能被中断地获取锁
 * 3.超时获取锁
 * @author gw.Zeng
 * @create 2019/5/15
 * @since 1.0.0
 */
public class LockDomeTest {
    //Lock API
    static Lock lock = new ReentrantLock(true);//重入锁
//    Condition condition = new Condition();
    public static void main(String[] args) {
        try {
            lock.lock();//获取锁
            lock.lockInterruptibly();//可中断的获取锁，即锁的获取中可以中断当前线程
           boolean b= lock.tryLock();//尝试非阻塞的获取锁，有返回 true/false
            lock.tryLock(100, TimeUnit.SECONDS);//超时获取锁，
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
