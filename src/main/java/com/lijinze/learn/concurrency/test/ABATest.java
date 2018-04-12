/**
 *  @描述：
 *  
 *  版本    作者        时间           邮箱                描述
 * =============================================================
 * v1.0   李金泽   2018年4月12日  lijinze@beixing360.com     初版
 * =============================================================
 * */
package com.lijinze.learn.concurrency.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Aaron
 *
 */
@Slf4j
public class ABATest {
	// 普通原子类
	private static AtomicInteger atomicInt = new AtomicInteger(100);
	// 有版本号的实现(参数是初始值与初始版本号)
	private static AtomicStampedReference<Integer> atomicStampedRef = new AtomicStampedReference<Integer>(100, 0);

	public static void main(String[] args) throws InterruptedException {
		// 模拟 B->A
		Thread intT1 = new Thread(new Runnable() {
			@Override
			public void run() {
				// A->B
				atomicInt.compareAndSet(100, 101);
				// B->A
				atomicInt.compareAndSet(101, 100);
			}
		});
		// 模拟 A->B
		Thread intT2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// 线程休眠，给 T1 执行
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// A->B
				boolean c3 = atomicInt.compareAndSet(100, 101);
				log.info("一般 CAS={}", c3);// 操作成功
			}
		});

		intT1.start();
		intT2.start();
		intT1.join();
		intT2.join();

		Thread refT1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// A-B 版本号 1
				atomicStampedRef.compareAndSet(100, 101, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
				// B-A 版本号 2
				atomicStampedRef.compareAndSet(101, 100, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
			}
		});

		Thread refT2 = new Thread(new Runnable() {
			@Override
			public void run() {
				// T2取出版本号
				int stamp = atomicStampedRef.getStamp();
				log.info("有版本号，线程休眠之前:stamp={}", stamp);
				try {
					// 休眠2秒
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// T1 的版本号
				log.info("有版本号，线程休眠之后:stamp={}", atomicStampedRef.getStamp());
				// A->B ，T1 将版本号增加到了2，然后执行时由于T2
				// 持有的版本号还是之前的0，与当前的版本号2不一致，最中CAS操作失败
				boolean c3 = atomicStampedRef.compareAndSet(100, 101, stamp, stamp + 1);
				log.info("有版本号 CAS={}", c3);
			}
		});

		refT1.start();
		refT2.start();
	}
}
