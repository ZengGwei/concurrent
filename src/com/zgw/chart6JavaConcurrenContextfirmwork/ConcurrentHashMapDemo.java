package com.zgw.chart6JavaConcurrenContextfirmwork;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈ConcurrentHashMap 的实现原理〉
 *
 * @author gw.Zeng
 * @create 2019/6/8
 * @since 1.0.0
 */
public class ConcurrentHashMapDemo {
    final HashMap<String, String> map = new HashMap<String, String>(2);
    public static void main(String[] args) {
        //ConcurrentHashMap的锁分段技术可有效提升并发访问率
        ConcurrentHashMap<String,String> map =new ConcurrentHashMap(25);

        map.put("name","zgw");

    }





    /**
     * 线程不安全的HashMap
     */
    public  void testUnsafeHashMap(){
        //put操作会使Entry链表形成环形结构

    }
  /**
     * 效率低下 HashTable
     */
    public  void testHashTavle(){
        // HashTable容器使用synchronized来保证线程安全

    }

}
