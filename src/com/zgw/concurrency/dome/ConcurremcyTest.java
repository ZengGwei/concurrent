package com.zgw.concurrency.dome;

/**
 * 〈〉
 *并发一定比 串行快吗？
 * @author gw.Zeng
 * @create 2019/5/9
 * @since 1.0.0
 */
public class ConcurremcyTest {

    public static void main(String[] args) {
        try {
            concurrent();
            serial();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void concurrent() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                long a= 5;
                long count = 1000000000;
                for (long i = 0; i <count ; i++) {
                    a +=10;
                }
            }
        });
        thread.start();
        long b = 0;
        for (long i = 0; i <1000000000 ; i++) {
            b-=1;
        }
        long time = System.currentTimeMillis()-start;
        thread.join();
        System.out.println("concurrent :"+time+"ms;b="+b);
    }
   private  static  void serial(){
       long start = System.currentTimeMillis();
       long a= 5;
       long count = 1000000000;
       for (long i = 0; i <count ; i++) {
           a +=10;
       }
       long b = 0;
       for (long i = 0; i <1000000000 ; i++) {
           b-=1;
       }
       long time = System.currentTimeMillis()-start;
       System.out.println("serial :"+time+"ms;b="+b);
   }



}
