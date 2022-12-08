package com.zlh.zk.lockdemo.factory;

import com.zlh.zk.lockdemo.ZkFairLock;
import com.zlh.zk.lockdemo.ZkLock;
import com.zlh.zk.lockdemo.ZkNonFairLock;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * @Author: ZhuLinHai
 **/
public class DefaultZkLockFactory extends AbstractZkLockFactory{

    private static Logger logger = LoggerFactory.getLogger(DefaultZkLockFactory.class);

    public DefaultZkLockFactory() {
        super();
    }

    @Override
    public ZkLock createLock(String lockPath,boolean fair) {
        ZooKeeper client = getZkClient();

        String path = getLockPath(lockPath);
        try {
            client.create(path,"".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (Exception e) {
            logger.error("has an error ",e);
        } finally {
            try {
                if (client.exists(path,null) == null) {
                    return null;
                }
                if (fair) {
                    // 创建公平锁
                    return new ZkFairLock(path,lockPath,client);
                } else {
                    // 创建非公平锁
                    return new ZkNonFairLock(path,lockPath,client);
                }
            } catch (Exception e) {
                logger.error("has an error ",e);
                return null;
            }
        }
    }
}
