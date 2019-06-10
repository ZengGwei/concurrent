package com.zgw.customBockingQue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 请结合ReentrantLock、Condition实现一个简单的阻塞队列，阻塞队列提供两个方法，一个是put、一个是take
 *
 * 当队列为空时，请求take会被阻塞，直到队列不为空
 *
 * 当队列满了以后，存储元素的线程需要备阻塞直到队列可以添加数据
 */
public class ZBlockQue<T>  {
    private Object[] que;
    private   int addIndex,removeIndex,count;//添加的下标，移除的下标，个数
    static  final  Lock lock = new ReentrantLock();
    Condition notEmpty = lock.newCondition();
    Condition notFull = lock.newCondition();


    public ZBlockQue(int size) {
        que = new Object[size];
    }

    /**
     * 添加
     * @param t
     */
    public void put(T t) throws InterruptedException {
        if (null==t){
            return;
        }
        try{
            lock.lock();
            while (count == que.length){
                System.out.println("full.....put await");
                notFull.await();
            }
            que[addIndex]=t;
            ++count;
            if (++addIndex == que.length){
                addIndex=0;
            }
            notEmpty.signal();//不为空 通知
        }finally {
            lock.unlock();
        }
    }

    public T  take() throws InterruptedException {
            try{
                lock.lock();
                while (count == 0){
                    System.out.println("empty.....take await ");
                    notEmpty.await();
                }
                Object obj= que[removeIndex];
                if (++removeIndex == que.length){
                    removeIndex = 0;
                }
                --count;
                notFull.signal();//有空位 通知
                return (T) obj;
            }finally {
                lock.unlock();
            }
    }

}
