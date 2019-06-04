package com.zgw.threadState;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;

/**
 * 线程的 启动 ->终止
 * 1.线程初始化时，会继承父线程相应的属性（线程优先级、是否守护、ThreadLocal 副本）
 *
 * @author gw.Zeng
 * @create 2019/5/29
 * @since 1.0.0
 */
public class ThreadStop {

    static ThreadLocal<Integer> data = new InheritableThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
//        ThreadStart();//线程的启动 初始化过程

        safeShutDownThread();//安全的终止线程

    }

    private static void safeShutDownThread() throws InterruptedException {
        SafeShutDown one = new SafeShutDown();
        Thread thread = new Thread(one, "Count++Thread");
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        //中断 thread
        thread.interrupt();

        SafeShutDown two = new SafeShutDown();
        thread = new Thread(two,"CountThread");
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        two.cancel();



    }


    /**
     * 中断状态是一个线程的 标识位
     */
    private static class SafeShutDown implements Runnable {
       private long i;
       private volatile boolean on = true;


        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()){//Thread.currentThread().isInterrupted()// 返回状态 true 复位
                i++;
            }
            System.out.println("count i="+i);
        }

        public  void cancel(){
            on =false;
        }
    }


    private static void ThreadStart() throws InterruptedException {

        data.set(34);
        System.out.println(currentThread().getName() + " before:" + data.get());
        System.out.println();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                Integer integer = data.get();
                System.out.println(currentThread().getName() + " :" + integer);
            } catch (InterruptedException e) {//复位
                e.printStackTrace();
            }
        }, "sub Thread-2");
        thread.start();
        thread.interrupt();//中断线程

        boolean interrupted = thread.isInterrupted();
        System.out.println(interrupted);
        Thread.sleep(100);
        System.out.println(thread.isInterrupted());
        System.out.println(currentThread().getName() + " after:" + data.get());


    }


}
