package com.zgw.concurrency.dome;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 〈〉
 *
 * @author gw.Zeng
 * @create 2019/5/18
 * @since 1.0.0
 */
public class ConditionNotify implements Runnable{
    //线程通信
    private Lock lock;

    private Condition condition;

    public ConditionNotify(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        
    }
}
