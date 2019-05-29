package com.zgw.threadState;

import java.util.concurrent.TimeUnit;

/**
 * 〈〉
 * 线程状态：new   runnable  waiting  time_waiting blocked   terminated
 * <p>
 * 查看 jps  -> jstack ID
 *
 * @author gw.Zeng
 * @create 2019/5/29
 * @since 1.0.0
 */
public class ThreadState {

    public static void main(String[] args) {

        new Thread(new TimeWaiting(), "TimeWaitingThread").start();

        new Thread(new Waiting(), " WaitingThread").start();

        new Thread(new Blocked(), "BlockedThread-1").start();

        new Thread(new Blocked(), "BlockedThread-2").start();
    }


    static class TimeWaiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Waiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class Blocked implements Runnable {
        @Override
        public void run() {
            synchronized (Blocked.class) {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
