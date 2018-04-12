/**
 *  @描述：
 *  
 *  版本    作者        时间           邮箱                描述
 * =============================================================
 * v1.0   李金泽   2018年4月12日  lijinze@beixing360.com     初版
 * =============================================================
 * */
package com.lijinze.learn.concurrency.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Aaron
 *
 */
@Slf4j
public class AtomicBooleanTest {

	private static AtomicBoolean ab = new AtomicBoolean(true);

	public static void main(String[] args) throws InterruptedException {
		ExecutorService es = Executors.newCachedThreadPool();
		int c = 1000;
		int s = 100;
		final Semaphore sh = new Semaphore(s);
		final CountDownLatch cdl = new CountDownLatch(c);
		for (int i = 0; i < c; i++) {
			es.execute(new Runnable() {
				@Override
				public void run() {
					try {
						sh.acquire();
						init();
						sh.release();
					} catch (InterruptedException e) {
						log.error("Error", e);
					}

					cdl.countDown();
				}
			});
		}

		cdl.await();
		es.shutdown();
	}

	private static void init() {
		// 个人理解这么写会有效率问题，因为每次都要进行CAS比较,应该加一层 if 判断,如 init2
		if (ab.compareAndSet(true, false)) {
			log.info(".......init.....OK");
		}
	}

	private static void init2() {
		if (ab.get()) {
			if (ab.compareAndSet(true, false)) {
				log.info(".......init.....OK");
			}
		}
	}
}
