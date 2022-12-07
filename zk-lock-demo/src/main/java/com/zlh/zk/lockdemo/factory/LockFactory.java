package com.zlh.zk.lockdemo.factory;

import com.zlh.zk.lockdemo.ZkLock;

/**
 * @Author: ZhuLinHai
 **/
public interface LockFactory {

    /**
     * 获取一个Lock 实例
     * @param lockPath
     * @return
     */
    public ZkLock getLock(String lockPath) ;

    public ZkLock getLock(String lockPath,boolean fair) ;
}
