package com.zlh.zk.lockdemo;

import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * @Author: ZhuLinHai
 **/
public class ZkNonFairLock implements ZkLock{

    private String lockPath;
    private String lockName;
    private ZkClient client;

    public ZkNonFairLock(String lockPath, String lockName, ZkClient client) {
        this.lockPath = lockPath;
        this.lockName = lockName;
        this.client = client;
    }

    @Override
    public void lock() {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public void waitForLock() {

    }
    @Override
    public void lockInterruptibly() throws InterruptedException {

    }



    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
