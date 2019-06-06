package com.zgw.concurrency.Lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 〈自定义 独占所〉
 *   同步器的设计是基于 模板方法 模式。使用者需要继承同步器并重写指定的方法。
 * @author gw.Zeng
 * @create 2019/6/5
 * @since 1.0.0
 */
public class Mutex implements Lock {
    private final Sync sync = new Sync();

    private static class Sync extends AbstractQueuedSynchronizer{
        //当状态为0 时时候获取锁
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0,1)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }
        //释放锁 将状态设置为0
        @Override
        protected boolean tryRelease(int arg) {
             if (getState()==0 ) throw new IllegalMonitorStateException();
             setExclusiveOwnerThread(null);
             setState(0);
             return true;
        }
        //是否是处于占用状态
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }
        //返回一个 Condition, 每个condition 都包含一个condition 队列
        Condition newCondition(){
            return new ConditionObject();
        }
    }

    //仅需要将操作代理到Sync上即可
    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
     return    sync.tryRelease(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean hasQueuedThreads(){
        return  sync.hasQueuedThreads();
    }
    public  boolean isLocked(){
        return  sync.isHeldExclusively();
    }

}
