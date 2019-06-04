package com.zgw.connectionPool;

/**
 * 〈〉
 *
 * @author gw.Zeng
 * @create 2019/6/4
 * @since 1.0.0
 */
public class taskThread implements Runnable{
    static  int count = 0;
    @Override
    public void run() {
        count++;
        System.out.println(count);
    }
}
