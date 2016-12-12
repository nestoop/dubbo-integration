package cn.nest;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

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

    void createNode(String nodePath, byte[] data) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = zkConnection.connet();

        String returnNode = zooKeeper.create(nodePath, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println("create node return code:" + returnNode);
    }
}
