package com.zgw.chart6JavaConcurrenContextfirmwork;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈ConcurrentHashMap 的实现原理〉
 * 请思考并回答以下问题
 * 1.ConcurrentHashMap1.8中是基于什么机制来保证线程安全性的
 *    答：  通过锁分离技术，由JDK7中锁segment 段 变更成 锁 Node节点 ；细化了锁的粒度
 *
 * 2.ConcurrentHashMap通过get方法获取数据的时候，是否需要通过加锁来保证数据的可见性？为什么？
 *    答： 不需要，数据put操作是CAS操作，已更新数据到主内存 。
 * 3. ConcurrentHashMap1.7和ConcurrentHashMap1.8有哪些区别？
 *   答： 1. 不采用segment而采用node，锁住node来实现减小锁粒度。
 *        2.设计了MOVED状态 当resize的中过程中 线程2还在put数据，线程2会帮助resize。
 *       3.使用3个CAS操作（tabAt(Node<K,V>[] tab, int i)、casTabAt（Node<K,V>[] tab, int i,Node<K,V> c, Node<K,V> v）、setTabAt(Node<K,V>[] tab, int i, Node<K,V> v)）
 *         来确保node的一些操作的原子性，这种方式代替了锁。
 *      4.sizeCtl的不同值来代表不同含义，起到了控制的作用。
 * 4.ConcurrentHashMap1.8为什么要引入红黑树？
 *   答：解决hash冲突 ，节点链表过长，造成读取数据效率低下，所以引入红黑树。
 */

public class ConcurrentHashMapDemo {
    final HashMap<String, String> map = new HashMap<String, String>(2);
    public static void main(String[] args) {
        //ConcurrentHashMap的锁分段技术可有效提升并发访问率
        ConcurrentHashMap<String,String> map =new ConcurrentHashMap(55);//this.sizeCtl = cap; 只设置了一个容量  a power of two table size


        /**
         *
         */
        map.put("name","zgw");


        map.size();


        String name = map.get("name");
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
