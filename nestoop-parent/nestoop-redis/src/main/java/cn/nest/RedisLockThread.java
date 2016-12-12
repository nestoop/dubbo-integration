package cn.nest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by botter
 *
 * @Date 7/12/16.
 * @description
 */
public class RedisLockThread {

    protected static Map<Long, Thread> currentThread = new ConcurrentHashMap<Long, Thread>();

    public static void setThreadMap(long id, Thread thread) {

       currentThread.put(id, thread);
    }

    public static Thread getThread(long id) {
       return currentThread.get(id);
    }

    public static void removeThread(long id) {
        currentThread.remove(id);
    }
}
