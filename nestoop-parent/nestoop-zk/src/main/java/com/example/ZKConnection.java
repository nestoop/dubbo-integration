package com.example;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by botter
 *
 * @Date 28/11/16.
 * @description
 */
@Component
public class ZKConnection {

    private String hosts;

    public static volatile CountDownLatch countDownLatch = new CountDownLatch(1);

    @Autowired
    public ZKConnection(@Value("${zookeeper.hosts}") String hosts) {
        this.hosts = hosts;
    }

    public ZooKeeper connet() throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(hosts, 5000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    countDownLatch.countDown();
                }

            }
        });

        countDownLatch.await();

        return zooKeeper;
    }

}