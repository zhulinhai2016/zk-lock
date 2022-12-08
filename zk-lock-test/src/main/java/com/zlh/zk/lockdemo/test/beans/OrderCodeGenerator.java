package com.zlh.zk.lockdemo.test.beans;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 订单编号生成类
 * OrderCodeGenerator
 */
public class OrderCodeGenerator {
	// 自增长序列
	private int i = 1;
	private Random random = new Random();

	// 按照“年-月-日-小时-分钟-秒-自增长序列”的规则生成订单编号
	public String getOrderCode() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-");
		try {
			int isd = random.nextInt(10);
			Thread.sleep(isd);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return sdf.format(now) + i++;
	}
	
	

	public static void main(String[] args) {
		OrderCodeGenerator ong = new OrderCodeGenerator();
		for (int i = 0; i < 10; i++) {
			System.out.println(ong.getOrderCode());
		}
	}

}
