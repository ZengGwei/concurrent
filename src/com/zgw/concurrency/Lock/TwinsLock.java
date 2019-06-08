package com.zgw.concurrency.Lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 〈自定义锁  只允许 两个线程访问资源〉
 *   自定义静态内部类 继承 AQS 重写 tryAcquireShared(int args)方法和tryReleaseShared(int args)方法，
 * @author gw.Zeng
 * @create 2019/6/7
 * @since 1.0.0
 */
public class TwinsLock implements Lock {
    private final Sync sync = new Sync(2);
    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }



    @Override
    public Condition newCondition() {
        return null;
    }

    private static final class Sync extends AbstractQueuedSynchronizer{
        Sync(int count){
            if (count<=0)
            throw new IllegalArgumentException("count must large than zero");
            setState(count);
        }
        @Override
        protected int tryAcquireShared(int reduceCount) {
             for(;;){
                 int current = getState();
                 int newCount = current - reduceCount;
                 if (newCount<0 ||compareAndSetState(current,newCount)){
                     return newCount;
                 }
             }
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for (;;){
                int current = getState();
                int newCount = current+arg;
                if (compareAndSetState(current,newCount)){
                    return true;
                }
            }
        }
    }

}
