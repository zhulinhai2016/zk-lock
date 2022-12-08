package com.zlh.zk.lockdemo.test;

import com.zlh.zk.lockdemo.test.beans.OrderServiceImpl;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 模拟并发测试
 */
public class ZkLockTest {

	public static void main(String[] args) {
		ZkLockTest test = new ZkLockTest();
		test.concurrentTest();
	}

	public void singleTest(){
		OrderServiceImpl orderService = new OrderServiceImpl();
		orderService.createOrder();
	}

	public void concurrentTest(){
		// 服务集群数量
		int service = 50;
		// 并发数量
		int requestSize = 100;
		CyclicBarrier cb = new CyclicBarrier(service*requestSize);

		for (int i= 0;i < service;i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					// 模拟分布式集群
					OrderServiceImpl orderService = new OrderServiceImpl();
					System.out.println(Thread.currentThread().getName() + "---------我准备好---------------");
					for (int j= 0;j < requestSize;j++){
						new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									cb.await();
								} catch (InterruptedException e) {
									e.printStackTrace();
								} catch (BrokenBarrierException e) {
									e.printStackTrace();
								}
								orderService.createOrder();
							}
						}).start();
					}
				}
			}).start();
		}
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
