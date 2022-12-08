package com.zlh.zk.lockdemo;

import org.apache.zookeeper.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.stream.Collectors;

/**
 * @Author: ZhuLinHai
 **/
public class ZkFairLock implements ZkLock {

    private String lockPath;
    private String lockName;
    private ZooKeeper client;
    private AtomicInteger state = new AtomicInteger(0);
    /**
     * 当前节点
     */
    private ThreadLocal<String> currentPath = new ThreadLocal<>();
    /**
     * 前一个节点
     */
    private ThreadLocal<String> previousPath = new ThreadLocal<>();


    public ZkFairLock(String lockPath, String lockName, ZooKeeper client) {
        this.lockPath = lockPath;
        this.lockName = lockName;
        this.client = client;
    }

    @Override
    public void lock() {
        if (!tryLock()) {
            // 阻塞等待被唤醒
            waitForLock();
            lock();
        }
    }

    @Override
    public boolean tryLock() {
        String rootPath = lockPath+"/";
        try {
            if (currentPath.get() == null || client.exists(currentPath.get(),null) == null) {
                String sequential = client.create(rootPath, "locked".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                currentPath.set(sequential);

            }
            // 获取子节点
            List<String> childrens = client.getChildren(lockPath,null);
            // 排序
            List<String> sortedChildrens = childrens.stream().sorted().collect(Collectors.toList());
            String curPath = currentPath.get().substring(rootPath.length());
            if (curPath.equals(sortedChildrens.get(0))) {
                // 如果第一个子节点就是自己，则表示获取到锁
                return true;
            } else {
                // 否则获取前一个节点信息，记录它，等待它的删除动作信号
                int currentIndex = sortedChildrens.indexOf(curPath);
                String prePath = sortedChildrens.get(currentIndex - 1);
                previousPath.set(rootPath + prePath);
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
        }
        return false;
    }

    @Override
    public void waitForLock() {
        // 未获得锁的线程进行等待
        CountDownLatch cdl = new CountDownLatch(1);
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (Event.EventType.NodeDeleted.equals(event.getType())) {
                    cdl.countDown();
                    System.out.println("节点被删除，去唤醒阻塞的线程："+Thread.currentThread().getName());
                }
            }
        };

        try {
            if (client.exists(previousPath.get(),watcher) != null) {
                // 等待
                cdl.await();
            }
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }

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
        try {
            if (currentPath.get() != null) {
                client.delete(currentPath.get(),-1);
                currentPath.set(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
