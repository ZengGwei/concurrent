package com.zgw.concurrency.Lock;

/**
 * 〈队列同步器〉
 * 内置FIFO队列来完成资源获取线程的排队工作，
 * 主要使用方式是子类（同步组件的静态内部类）继承同步器并实现它的抽象方法来管理同步状态。
 * 它仅仅只是定义了若干状态获取和释放的方法来供自定义同步组件使用。
 *
 * @author gw.Zeng
 * @create 2019/5/18
 * @since 1.0.0
 */
public class AQSDome {
    //同步器的设计是基于 模板方法 模式。使用者需要继承同步器并重写指定的方法。
    // 同步器提供 getState(); setState(int newState); compareAndSetState(int expect,int update);
    /**
     * 同步器可重写的方法:
     */
    // boolean    tryAcquire(int arg) ;  独占式获取同步状态，实现该方法需要查询当前状态并判断同步状态是否符合预期，
    //                           然后CAS同步状态
//     boolean  tryRelease(int arg) :独占式释放同步状态，等待获取同步状态的线程将有机会获取同步状态

    //tryAcquireShared(int arg) 共享式获取同步状态，tryRelease（int arg） 共享式释放
    //boolean isHeldExclusively() 是否被当前线程锁独占

    /**
     * 同步器提供的模板方法
     */
    // acquire(int arg) 独占式获取同步状态，若未获取成功，将进入同步队列等待，会调用重写的tryAcquire(int arg)
    //....
    /**
     * 自定义实现一个独占锁 {@code Mutex}
     */
//同步器实现分析{}

    /**
     * *1.同步队列（一个FIFO双向队列）
     * 当线程获取同步状态失败-->同步器会将当前线程以及等待状态等信息构成一个节点 加入同步队列
     * 同时会阻塞当前线程，当同步状态释放时，会把首节点线程唤醒，使其再次尝试获取同步状态。
     * <p>
     * NODE 节点 包含   int waitStatus;  Node prev; Node next;   Thread thread;  Node nextWaiter;
     * 没有成功获取同步状态的线程会成为节点加入该队列的尾部
     * <p>
     * **************************************
     * * 2.独占式同步状态获取与释放
     * 同步器的 acquire(int arg) 方法可以获取同步状态。
     * public final void acquire(int arg) { if (!tryAcquire(arg) && acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
     * selfInterrupt();
     * }
     * 主要完成了同步状态获取{@tryAcquire(arg)}、节点构造、加入同步队列{@addWaiter(Node.EXCLUSIVE), arg)}以及在同步队列中自旋等 待的相关工作，
     * <p>
     * 节点进入同步队列后，不断自旋{@acquireQueued()
     * 总结：
     * 在获取同步状态时，同步器维 护一个同步队列，获取状态失败的线程都会被加入到队列中并在队列
     * 中进行自旋；移出队列 或停止自旋）的条件是前驱节点为头节点且成功获取了同步状态。在释放同
     * 步状态时，同步 器调用tryRelease(int arg)方法释放同步状态，然后唤醒头节点的后继节点。
     * ********************************************
     * *3.共享式同步状态获取与释放
     *
     *
     */


    public static void main(String[] args) {

    }
}
