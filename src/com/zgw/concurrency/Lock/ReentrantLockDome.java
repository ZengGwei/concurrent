package com.zgw.concurrency.Lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 〈重入suo〉
 */
public class ReentrantLockDome {
    private Lock lock = new ReentrantLock(true);

    private  void process(){
        try {
            lock.lock();
            System.out.println("执行加锁 模块");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }finally {
            lock.unlock();
        }


    }


    public static void main(String[] args) {
        ReentrantLockDome doem= new ReentrantLockDome();
        for (int i = 0; i <10 ; i++) {

            int num = i;
            new Thread(()->{
                System.out.println("Thread "+ num +"开始执行。。。");
                doem.process();

            }).start();

        }

    }




}
