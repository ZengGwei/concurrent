package com.zgw;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 〈〉
 *
 * @author gw.Zeng
 * @create 2019/5/29
 * @since 1.0.0
 */
public class MultiThread {
    public static void main(String[] args) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        for (ThreadInfo info:threadInfos){
            System.out.println(info.getThreadId()+" name:"+info.getThreadName() +" state:"+info.getThreadState());

        }

    }



}
