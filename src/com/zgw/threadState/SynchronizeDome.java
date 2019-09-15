package com.zgw.threadState;

/**
 * 〈、〉
 *  javap -verbose  查看编译结果
 * @author gw.Zeng
 * @create 2019/5/31
 * @since 1.0.0
 */
public class SynchronizeDome {
    private static int i;
    public static void main(String[] args) {
        synchronized (SynchronizeDome.class){
            i++;
        }
        addOper();
    }
    public  static synchronized void    addOper(){
        i++;
    }
}
