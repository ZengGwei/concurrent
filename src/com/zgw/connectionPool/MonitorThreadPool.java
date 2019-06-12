package com.zgw.connectionPool;

import java.util.Date;
import java.util.concurrent.*;

/**
 * 线程池监控
 */
public class MonitorThreadPool extends ThreadPoolExecutor {
    private ConcurrentHashMap<String, Date> startTimes;

    public MonitorThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.startTimes = new ConcurrentHashMap<>();
    }

    public static ExecutorService newCacheThreadPool(){
      return new MonitorThreadPool(0,Integer.MAX_VALUE,60L,TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
    }

    @Override
    public void shutdown() {
        System.out.println("任务 shutdown" );
        System.out.println("已经执行的任务数："+this.getCompletedTaskCount() );
        System.out.println( "当前活动线程数:"+this.getActiveCount());
        System.out.println( "当前排队线程数:"+this.getQueue().size());
        super.shutdown();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        startTimes.put(String.valueOf(r.hashCode()),new Date());
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        Date startDate = startTimes.remove(String.valueOf(r.hashCode()));
        long dif = new Date().getTime() - startDate.getTime();

        System.out.println("任务after");
        System.out.println("任务耗时："+dif+"ms");
        System.out.println("初始线程："+this.getPoolSize());
        System.out.println("核心线程"+this.getCorePoolSize());
        System.out.println("正在执行任务数量："+this.getActiveCount());
        System.out.println("已经执行的任务数："+this.getCompletedTaskCount());
        System.out.println("任务总数："+this.getTaskCount());
        System.out.println("最大允许的线程数："+this.getMaximumPoolSize());
        System.out.println("线程空闲时间："+this.getKeepAliveTime(TimeUnit.MILLISECONDS));
        System.out.println("");
        super.afterExecute(r, t);
    }

    public static void main(String[] args) {
        ExecutorService executorService = MonitorThreadPool.newCacheThreadPool();
        for (int i = 0; i <100 ; i++) {
            executorService.execute(new TaskThread());
        }
        executorService.shutdown();


    }
}
