package com.zgw.concurrency.Lock;


/**
 * 〈自定义 2线程访问资源测试〉
 *  每次最多两线程访问
 * @author gw.Zeng
 * @create 2019/6/7
 * @since 1.0.0
 */
public class TwinsLockDome {
    public static void main(String[] args) throws InterruptedException {
        TwinsLock lock = new TwinsLock();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                while (true) {
                    lock.lock();
                    try {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    } finally {
                        lock.unlock();
                    }

                }
            }, "thread" + i);
            thread.setDaemon(true);
            thread.start();
        }
//        // 每隔1秒换行
//        for (int i = 0; i < 10; i++) {
//            Thread.sleep(1000);
//            System.out.println();
//        }
    }
}
