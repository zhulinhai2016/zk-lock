package com.zlh.zk.lockdemo.test.beans;

/**
 * 订单编号生成类
 * OrderCodeGenerator
 */
public class OrderCodeGeneratorSingle {
	
	static class InstanceHolder {
		private static OrderCodeGenerator instance = new OrderCodeGenerator();
	}
	
	public static OrderCodeGenerator getInstance() {
		return InstanceHolder.instance;
	}
}
