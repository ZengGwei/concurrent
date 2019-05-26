package com.zgw.concurrency.dome;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 〈〉
 *
 * @author gw.Zeng
 * @create 2019/5/26
 * @since 1.0.0
 */
public class ReentrantReadWriteLockDome {
    static   ReentrantReadWriteLock rrw = new ReentrantReadWriteLock();
    static   Lock read = rrw.readLock();
    static   Lock write = rrw.writeLock();

    private  int data = 0;

    private void  read(){
        try {
            read.lock();
            System.out.println("read:"+data);
        }catch (Exception e){

        }finally {
            read.unlock();
        }
    }
    private void write(){
        try{
            write.lock();
            System.out.println("write:"+data);
            data++;
        }catch (Exception e){

        }finally {
            write.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantReadWriteLockDome dome = new ReentrantReadWriteLockDome();

        for (int i = 0; i <10 ; i++) {
            new Thread(()->{
                dome.read();
            }).start();
            new Thread(()->{
                dome.write();
            }).start();
        }

    }

}
