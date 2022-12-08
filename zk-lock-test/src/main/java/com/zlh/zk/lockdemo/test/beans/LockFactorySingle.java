package com.zlh.zk.lockdemo.test.beans;

import com.zlh.zk.lockdemo.factory.DefaultZkLockFactory;
import com.zlh.zk.lockdemo.factory.LockFactory;

/**
 * @Author: ZhuLinHai
 **/
public class LockFactorySingle {

    static class InstanceHolder{
        private static LockFactory instance = new DefaultZkLockFactory();
    }

    public static LockFactory getInstance(){
        return InstanceHolder.instance;
    }
}
