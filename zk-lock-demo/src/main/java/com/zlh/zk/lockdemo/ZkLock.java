package com.zlh.zk.lockdemo;

import java.util.concurrent.locks.Lock;

/**
 * @Author: ZhuLinHai
 **/
public interface ZkLock extends Lock {

    public void waitForLock();

}
