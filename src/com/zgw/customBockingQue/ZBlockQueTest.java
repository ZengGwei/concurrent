package com.zgw.customBockingQue;

/**
 * create by zenggw at 2019-06-09;
 */
public class ZBlockQueTest {
    /**
     * 请结合ReentrantLock、Condition实现一个简单的阻塞队列，阻塞队列提供两个方法，一个是put、一个是take
     *
     * 当队列为空时，请求take会被阻塞，直到队列不为空
     *
     * 当队列满了以后，存储元素的线程需要备阻塞直到队列可以添加数据
     */
    private static final  ZBlockQue<String> que = new ZBlockQue(8);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("start ---------");
        for (int i = 0; i < 20; i++) {
            int finalI = i;

            new Thread(() -> {
                try {
                    que.put("thread" + finalI);
                    System.out.println("start ---put " + finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "putThread").start();

            new Thread(() -> {
                try {
                    Thread.sleep(100);
                    String take = que.take();
                    System.out.println("take =====> " + take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "takeThread").start();
        }
    }

}
