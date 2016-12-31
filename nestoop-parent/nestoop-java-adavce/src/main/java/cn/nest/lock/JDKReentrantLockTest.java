package cn.nest.lock;

import java.net.URLDecoder;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by perk
 * 使用JDK的锁机制
 * @DATE 2016/12/26
 * @DESC
 */
public class JDKReentrantLockTest {

    private static final ReentrantLock lock = new ReentrantLock();

    private int number;

    static class ThreadLock1 implements Runnable {

        private JDKReentrantLockTest jdkLock;

        public ThreadLock1(JDKReentrantLockTest jdkLock){
            this.jdkLock = jdkLock;
        }

        @Override
        public void run() {
            lock.lock();
            try{
                jdkLock.setNumber(jdkLock.getNumber() + 1);
                System.out.println(Thread.currentThread().getName() + "  number: " + jdkLock.getNumber());
            }finally {
                lock.unlock();
            }

        }
    }

    static class ThreadLock2 implements Runnable {

        private JDKReentrantLockTest jdkLock;

        public ThreadLock2(JDKReentrantLockTest jdkLock){
            this.jdkLock = jdkLock;
        }

        @Override
        public void run() {
            lock.lock();
            try{
                jdkLock.setNumber(jdkLock.getNumber() + 1);
                System.out.println(Thread.currentThread().getName() + " number: " + jdkLock.getNumber());
            }finally {
                lock.unlock();
            }

        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public static void main (String[] args) {
        JDKReentrantLockTest jdkReentrantLockTest = new JDKReentrantLockTest();

        for (int i =0 ;i <10 ; i++) {
           Thread thread1 = new Thread(new ThreadLock1(jdkReentrantLockTest));
            thread1.setName("ThreadLock1-" + (i+1));
            thread1.start();

            Thread thread2 = new Thread(new ThreadLock2(jdkReentrantLockTest));
            thread2.setName("ThreadLock2-" + (i+1));
            thread2.start();
        }

        System.out.println(URLDecoder.decode("http://openauth.alipay.com/oauth2/appToAppAuth.htm?app_id=2016012901129787&redirect_uri=http%3A%2F%2Fdm1.devel.2dupay.com%2Fsp%2Falipay%2Fisv.html"));
    }
}
