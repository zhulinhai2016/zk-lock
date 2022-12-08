package com.zlh.zk.lockdemo.factory;

import com.zlh.zk.lockdemo.ZkLock;
import lombok.Data;
import org.apache.zookeeper.ZooKeeper;

/**
 * @Author: ZhuLinHai
 **/
@Data
public abstract class AbstractZkLockFactory implements LockFactory{

    /**
     * 锁的根节点
     *
     */
    private String zklock_root_path = "/zklock/locks";

    private ZooKeeper zkClient;
    /**
     * conect 超时时间 单位ms
     */
    private static final int CONNCECTION_TIMEOUT = 5000;
    /**
     * session 超时时间 单位ms
     */
    private static final int SESSION_TIMEOUT = 30000;

    /**
     * 默认服务器
     */
    private static final String DEFAULT_SERVERS = "192.168.2.33:2181";

    /**
     * 默认实现单机版的分布式锁
     */
    public AbstractZkLockFactory() {
        try {
            this.zkClient = new ZooKeeper(DEFAULT_SERVERS,SESSION_TIMEOUT,null);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public AbstractZkLockFactory(ZooKeeper client) {
        this.zkClient = client;
    }

    @Override
    public ZkLock getLock(String lockPath) {
        return createLock(lockPath,true);
    }

    @Override
    public ZkLock getLock(String lockPath, boolean fair) {
        return createLock(lockPath,fair);
    }

    public ZkLock createLock(String lockPath,boolean fair){
        throw new UnsupportedOperationException("方法未实现！");
    }
    public String getLockPath(String lockKey) {
        if (lockKey.startsWith("/")) {
            return zklock_root_path+lockKey;
        }
        return zklock_root_path+"/"+lockKey;
    }
}
