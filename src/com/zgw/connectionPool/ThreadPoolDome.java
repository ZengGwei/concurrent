package com.zgw.connectionPool;

import java.util.concurrent.*;

/**
 * 〈〉
 *0、为什么要使用线程池？
 *   答：如果每个请求都就创建一个新线程，创建和销毁线程花费的时间和cpu资源消耗都相当大
 *       如果再一个jvm里面创建太多线程，可能会使系统由于过度消耗内存或“切换过度”而导致系统资源不足。
 *       采用线程池优点：就可以提前创建好若干线程放在容器中。如果有任务需要处理，就分配给线程池中的线程来执行就行
 *       ，任务处理完后这个线程不会被销毁，而是等待后续分配任务。通过重复管理大量线程来避免重复创建线程。
 *

 *1、 Executors提供的四种线程池:newSingleThreadExecutor,newFixedThreadPool,newCachedThreadPool,newScheduledThreadPool ，请说出他们的区别以及应用场景
 *  答：newSingleThreadExecutor   只创建一个线程来处理任务的线程池，多个任务会进入一个FIFO的队列 等待处理
 *                             用于负载比较大的服务器，为了资源的合理利用，需要限制当前线 程数量
 *      newFixedThreadPool        法返回一个固定数量的线程池，线程数不变，当有一个任务提交 时，若线程池中空闲，则立即执行，若没有，则会被暂缓在一个任务队列中，等待有空闲的 线程去执行。
        newCachedThreadPool       一个可根据实际情况调整线程个数的线程池，不限制最大线程 数量   并且每一个空闲线程会在 60 秒 后自动回收
        newScheduledThreadPool   创建一个可以指定线程的数量的线程池，但是这个线程池还带有 延迟和周期性执行任务的功能，类似定时器

 *2、线程池有哪几种工作队列？
 *    答  LinkedBlockingQueue()     SynchronousQueue()  DelayedWorkQueue()  ArrayBlockingQueue()
 *
 * 3、线程池默认的拒绝策略有哪些
 *     AbortPolicy：直接抛出异常，默认策略；
 *     CallerRunsPolicy：用调用者所在的线程来执行任务；
 *     DiscardOldestPolicy：丢弃阻塞队列中靠最前的任务，并执行当前任务；
 *     DiscardPolicy：直接丢弃任务；
 *
 * 4、如何理解有界队列和无界队列
 *     答：有界队列 ：有固定大小的阻塞队列 ；无界队列：无固定大小的阻塞队列
 *
 *5、 线程池是如何实现线程回收的？ 以及核心线程能不能被回收？
 *    答：runWork()
 *
 *6、 FutureTask是什么
 *
 * 7、Thread.sleep(0)的作用是什么
 *
 * 8、如果提交任务时，线程池队列已满，这时会发生什么
 *
 * 9、如果一个线程池中还有任务没有执行完成，这个时候是否允许被外部中断？

 */
public class ThreadPoolDome {
    static  ExecutorService executor= Executors.newFixedThreadPool(5);
    static  ExecutorService singleEX= Executors.newSingleThreadExecutor();
    static  ExecutorService cachedEx= Executors.newCachedThreadPool();//返回一个可根据实际情况调整线程个数的线程池，不限制最大线程 数量，
                                                                     // 若用空闲的线程则执行任务，若无任务则不创建线程。并且每一个空闲线程会在 60 秒
                                                                    //  后自动回收

    static  ExecutorService scheduleEx= Executors.newScheduledThreadPool(3);

    //以上 都是基于ThreadpoolExecutor 来构建的

    /*
     *  ThreadPoolExecutor(int corePoolSize,       核心线程数
     *                               int maximumPoolSize,        最大线程数
     *                               long keepAliveTime,         最大线程书存活时间
     *                               TimeUnit unit,               时间单位
     *                               BlockingQueue<Runnable> workQueue,   保存任务队列
     *                               ThreadFactory threadFactory,         创建新线程使用的工厂
     *                               RejectedExecutionHandler handler     当任务无法执行的时候的处理方式
     */

    //

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        for (int i = 0; i <20 ; i++) {
//           singleEX.execute(new TaskThread());
           executor.execute(new TaskThread());
         Future future =  singleEX.submit((Callable<Object>) new TaskThread());
            System.out.println(future.get());
        }
        singleEX.shutdown();

    }


    /*
    源码分析：
    1.ctl   是AtomicInteger类==》  存线程数 和 线程池状态。31位bit  高3位 保存运行状态 低29位保存线程数
      int COUNT_BITS = Integer.SIZE - 3;  29
      int CAPACITY   = (1 << COUNT_BITS) - 1;      0000 1111 1111 1111 1111 1111 1111 1111
      int RUNNING    = -1 << COUNT_BITS;  -1<<29=  1110 0000 0000 0000 0000 0000 0000 0000
      int SHUTDOWN   =  0 << COUNT_BITS;  0<<29=   0000 0000 0000 0000 0000 0000 0000 0000
      int STOP       =  1 << COUNT_BITS;  1<<29=   0010 0000 0000 0000 0000 0000 0000 0000
      int TIDYING    =  2 << COUNT_BITS;  2<<29=   0100 0000 0000 0000 0000 0000 0000 0000
      int TERMINATED =  3 << COUNT_BITS;  3<<29=   0110 0000 0000 0000 0000 0000 0000 0000

      int ctlOf(RUNNING, 0) { return rs | wc; }   // 返回

      int runStateOf(int c)     { return c & ~CAPACITY; } c = 1110 0000 0000 0000 0000 0000 0000 0111
                                                             &1111 0000 0000 0000 0000 0000 0000 0000 =1110 ...
      int workerCountOf(int c)  { return c & CAPACITY; }  c = 1110 0000 0000 0000 0000 0000 0000 0111     7个线程
                                                             &0000 1111 1111 1111 1111 1111 1111 1111     ==  ... 0111
     */


}
