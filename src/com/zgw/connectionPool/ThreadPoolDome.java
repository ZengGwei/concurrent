package com.zgw.connectionPool;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 〈〉
 *
 * @author gw.Zeng
 * @create 2019/6/4
 * @since 1.0.0
 */
public class ThreadPoolDome {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i <10 ; i++) {
            executorService.submit(new taskThread());
        }

    }



}
