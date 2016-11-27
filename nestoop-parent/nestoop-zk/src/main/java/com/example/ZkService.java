package com.example;

import org.apache.zookeeper.*;
import org.apache.zookeeper.client.ZooKeeperSaslClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by botter
 *
 * @Date 27/11/16.
 * @description
 */
@Service
public class ZkService {

    @Autowired
    private ZKConnection zkConnection;

    public void createNode(String nodePath, byte[] data) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = zkConnection.connet();

        String returnNode = zooKeeper.create(nodePath, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println("create node return code:" + returnNode);
    }
}
