package com.zgw.connectionPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 〈〉
 *为什么要使用线程池？
 *1/、 Executors提供的四种线程池:newSingleThreadExecutor,newFixedThreadPool,newCachedThreadPool,newScheduledThreadPool ，请说出他们的区别以及应用场景
 *2、线程池有哪几种工作队列？
 * 3、线程池默认的拒绝策略有哪些
 * 4、如何理解有界队列和无解队列
 *5、 线程池是如何实现线程回收的？ 以及核心线程能不能被回收？
 *6、 FutureTask是什么
 * 7、Thread.sleep(0)的作用是什么
 * 8、如果提交任务时，线程池队列已满，这时会发生什么
 * 9、如果一个线程池中还有任务没有执行完成，这个时候是否允许被外部中断？

 */
public class ThreadPoolDome {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i <10 ; i++) {
            executorService.submit(new taskThread());
        }
        executorService.shutdown();

    }



}
