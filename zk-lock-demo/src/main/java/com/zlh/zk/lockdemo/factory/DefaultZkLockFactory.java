package com.zlh.zk.lockdemo.factory;

import com.zlh.zk.lockdemo.ZkFairLock;
import com.zlh.zk.lockdemo.ZkLock;
import com.zlh.zk.lockdemo.ZkNonFairLock;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: ZhuLinHai
 **/
public class DefaultZkLockFactory extends AbstractZkLockFactory{

    private static Logger logger = LoggerFactory.getLogger(DefaultZkLockFactory.class);

    @Override
    public ZkLock createLock(String lockPath,boolean fair) {
        ZkClient client = getClient();

        String path = getLockPath(lockPath);
        try {
            client.createEphemeral(path);
        } catch (Exception e) {
            logger.error("has an error ",e);
        } finally {
            try {
                if (!client.exists(path)) {
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
