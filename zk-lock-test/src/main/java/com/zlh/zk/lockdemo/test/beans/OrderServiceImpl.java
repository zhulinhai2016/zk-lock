package com.zlh.zk.lockdemo.test.beans;


import com.zlh.zk.lockdemo.ZkLock;
import com.zlh.zk.lockdemo.factory.LockFactory;

import java.util.HashSet;
import java.util.Set;


/**
 * 
 */
public class OrderServiceImpl {
	
	private OrderCodeGenerator ocg = OrderCodeGeneratorSingle.getInstance();

	LockFactory lockFactory = LockFactorySingle.getInstance();

	private ZkLock lock = lockFactory.getLock("/orderLock");
	
	// 重复编号集合
	private static Set<String> codeSet = new HashSet<String>();

	// 创建订单接口
	public void createOrder() {

		String orderCode = null;
		lock.lock();
		try {
			// 获取订单号
			orderCode = ocg.getOrderCode();
			
			if(codeSet.contains(orderCode)) {
				System.err.println("重复编号："+orderCode);
			}else{
				codeSet.add(orderCode);
			}

		} finally {
			lock.unlock();
		}

		System.out.println(Thread.currentThread().getName() + " =============>" + orderCode);

		// ……业务代码，此处省略100行代码

	}

}
