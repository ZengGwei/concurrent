package com.zgw.connectionPool;

import java.util.concurrent.Callable;

/**
 * 〈〉
 *
 * @author gw.Zeng
 * @create 2019/6/4
 * @since 1.0.0
 */
public class TaskThread implements Runnable, Callable {
    public static  int count = 0;
    @Override
    public void run() {
        count++;
        System.out.println(count);

    }

    @Override
    public Object call() throws Exception {
        count++;
        return count;
    }
}
