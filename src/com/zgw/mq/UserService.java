package com.zgw.mq;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 〈7种阻塞队列〉
 *  ArrayBlockingQueue 数组实现的有界阻塞队列, 此队列按照先进先出（FIFO）的原则 对元素进行排序。
 *  LinkedBlockingQueue  链表实现的有界阻塞队列, 此队列的默认和最大长度为 Integer.MAX_VALUE。此队列按照先进先出的原则对元素进行 排序
 *  PriorityBlockingQueue  支持优先级排序的无界阻塞队列, 默认情况下元素采取自然顺序 升序排列。
 *  DelayQueue  优先级队列实现的无界阻塞队列
 *  SynchronousQueue  不存储元素的阻塞队列, 每一个 put 操作必须等待一个 take 操 作，否则不能继续添加元素。
 *  LinkedTransferQueue 链表实现的无界阻塞队列
 *  LinkedBlockingDeque 链表实现的双向阻塞队列
 *
 */
public class UserService {
    private static ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(16);
    private static LinkedBlockingQueue<Integer> linkedQueue = new LinkedBlockingQueue<Integer>();
    private static PriorityBlockingQueue<Integer> priorityQueue = new PriorityBlockingQueue<Integer>(); //默认11
    private static DelayQueue delayQueue = new DelayQueue();
    private static SynchronousQueue synQueue = new SynchronousQueue();

    static final AtomicInteger count = new AtomicInteger();//原子类

    public static void main(String[] args) throws InterruptedException {
       Thread threadRegister =  new Thread(()->{
            for (int i = 0; i <26 ; i++) {
                try {
                    int i1 = count.incrementAndGet();
                    new UserService().register(i,"18200399003", "123456");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        },"thread-->register") ;
       threadRegister.start();
        Thread.sleep(100);
       Thread threadTake =   new Thread(() -> {
            try {
                for (int i = 0; i <30 ; i++) {
                    Integer take = queue.take();
                    new CreditsService().addCredits(take);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread-->take") ;
       threadTake.start();

       Thread.sleep(5000);

       threadRegister.interrupt();
       threadTake.interrupt();

    }

    public boolean register(int userId,String phone, String passward) throws InterruptedException {
//        int userId = UUID.randomUUID().toString().replaceAll("-", "").hashCode();
        //**code
        System.out.println("用户注册成功！=="+userId);
        queue.add(userId);//加积分
        return true;
    }
}
