/**
 *  @描述：
 *  
 *  版本    作者        时间           邮箱                描述
 * =============================================================
 * v1.0   李金泽   2018年4月11日  lijinze@beixing360.com     初版
 * =============================================================
 * */
package com.lijinze.learn.concurrency.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.lijinze.learn.annotations.NotThreadSafe;

import lombok.extern.slf4j.Slf4j;

/**
 * 并发模拟
 * 
 * @author Aaron
 *
 */
@NotThreadSafe
@Slf4j
public class ConcurrencyTest1 {

	// 模拟 1000个用户请求
	private final static int TotalClient = 1000;

	// 限制同一时间只能有10个线程执行
	private final static int TotalThread = 10;
	// 计数器
	private static int count = 0;

	/**
	 * @描述
	 * @param
	 * @return void
	 * @throws InterruptedException
	 * @exception @author
	 *                李金泽
	 * @Time 2018年4月11日 下午2:04:33
	 */
	public static void main(String[] args) throws InterruptedException {
		ExecutorService es = Executors.newCachedThreadPool();
		// 设置信号量，允许同时最多执行的线程数
		final Semaphore sp = new Semaphore(TotalThread);
		final CountDownLatch cdl = new CountDownLatch(TotalClient);
		for (int i = 0; i < TotalClient; i++) {
			es.execute(new Runnable() {
				@Override
				public void run() {
					try {
						sp.acquire();
						add();
						sp.release();
					} catch (InterruptedException e) {
						log.error("A", e);
					}
					cdl.countDown();
				}
			});
		}
		// 中断主线层代码，直至countdownlatch 的计数器变为0
		cdl.await();
		es.shutdown();
		log.info(String.valueOf(count));
	}

	@NotThreadSafe
	public static void add() {
		count++;
	}

}
