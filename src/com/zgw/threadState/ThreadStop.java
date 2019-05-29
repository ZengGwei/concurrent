package com.zgw.threadState;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;

/**
 *线程的 启动 ->终止
 * 1.线程初始化时，会继承父线程相应的属性（线程优先级、是否守护、ThreadLocal 副本）
 * @author gw.Zeng
 * @create 2019/5/29
 * @since 1.0.0
 */
public class ThreadStop {

    static  ThreadLocal<Integer> data = new InheritableThreadLocal<>();
    public static void main(String[] args) throws InterruptedException {
        data.set(34);
        System.out.println(currentThread().getName()+" before:"+data.get());
        System.out.println( );
        Thread thread=new Thread(()->{
            try {
                Thread.sleep(100);
                Integer integer = data.get();
                System.out.println(currentThread().getName()+" :"+integer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"sub Thread") ;
        thread.start();
        thread.join();

        System.out.println(currentThread().getName()+" after:"+data.get());

    }

}
