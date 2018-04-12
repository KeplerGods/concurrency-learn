/**
 *  @描述：
 *  
 *  版本    作者        时间           邮箱                描述
 * =============================================================
 * v1.0   李金泽   2018年4月12日  lijinze@beixing360.com     初版
 * =============================================================
 * */
package com.lijinze.learn.concurrency.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Aaron
 *
 */
@Slf4j
public class SynchronizedTest {

	/**
	 * 未做任何修饰
	 */
	public void test(int n) {
		for (int i = 0; i < 5; i++) {
			log.info("test->{}-{}", n, i);
		}
	}

	/**
	 * 修饰代码块
	 */
	public void test1(int n) {
		log.info("代码块A-{}", n);
		synchronized (this) {
			for (int i = 0; i < 5; i++) {
				log.info("test1->{}-{}", n, i);
			}
		}
		log.info("代码块B-{}", n);
	}

	/**
	 * 修饰方法
	 */
	public synchronized void test2(int n) {
		log.info("方法A-{}", n);
		for (int i = 0; i < 5; i++) {
			log.info("test2->{}-{}", n, i);
		}
		log.info("方法B-{}", n);
	}

	/**
	 * 修饰静态方法
	 */
	public synchronized static void test3(int n) {
		log.info("静态方法A-{}", n);
		for (int i = 0; i < 5; i++) {
			log.info("test3->{}-{}", n, i);
		}
		log.info("静态方法B-{}", n);
	}

	/**
	 * 锁类
	 */
	public void test4(int n) {
		synchronized (this.getClass()) {
			log.info("类A-{}", n);
			for (int i = 0; i < 5; i++) {
				log.info("test4->{}-{}", n, i);
			}
			log.info("类B-{}", n);
		}
	}

	public static void main(String[] args) {
		/* 每个需要单独执行 */
		oneTest();
		// twoTest();
		// threeTest();
		// fourTest();
		// allTest1();
		// allTest2();
	}

	// 测试修饰代码块
	public static void oneTest() {
		// 先输执行非同步块，但是不包含同步块之后的代码，然后执行同步块代码，最后执行同步块之后的代码
		final SynchronizedTest st = new SynchronizedTest();
		ExecutorService es = Executors.newCachedThreadPool();
		es.execute(new Runnable() {
			@Override
			public void run() {
				st.test1(1);
			}
		});

		es.execute(new Runnable() {
			@Override
			public void run() {
				st.test1(2);
			}
		});

		es.shutdown();
	}

	// 测试修饰方法
	public static void twoTest() {
		// 在我本机测试结果是，先执行完 21，在执行22
		final SynchronizedTest st = new SynchronizedTest();
		ExecutorService es = Executors.newCachedThreadPool();
		es.execute(new Runnable() {
			@Override
			public void run() {
				st.test2(21);
			}
		});

		es.execute(new Runnable() {
			@Override
			public void run() {
				st.test2(22);
			}
		});

		es.shutdown();
	}

	// 测试修饰静态方法
	public static void threeTest() {
		// 在我本机测试结果是，先执行完31，后执行32
		ExecutorService es = Executors.newCachedThreadPool();
		es.execute(new Runnable() {
			@Override
			public void run() {
				SynchronizedTest.test3(31);
			}
		});

		es.execute(new Runnable() {
			@Override
			public void run() {
				SynchronizedTest.test3(32);
			}
		});

		es.shutdown();
	}

	// 测试锁class
	public static void fourTest() {
		// 在我本机结果是，顺序执行，先执行完 41 ，然后执行 42
		final SynchronizedTest st = new SynchronizedTest();
		final SynchronizedTest st2 = new SynchronizedTest();
		ExecutorService es = Executors.newCachedThreadPool();
		es.execute(new Runnable() {
			@Override
			public void run() {
				st.test4(41);
			}
		});

		es.execute(new Runnable() {
			@Override
			public void run() {
				st2.test4(42);
			}
		});

		es.shutdown();
	}

	// 综合测试，测试静态方法与非静态方法同时调用
	public static void allTest1() {
		// 在我本机测试结果是，静态方法总是先执行，然后是非静态方法
		final SynchronizedTest st = new SynchronizedTest();
		ExecutorService es = Executors.newCachedThreadPool();
		es.execute(new Runnable() {
			@Override
			public void run() {
				st.test1(51);
			}
		});

		es.execute(new Runnable() {
			@Override
			public void run() {
				SynchronizedTest.test3(52);
			}
		});
		es.shutdown();
	}

	// 综合测试,测试非加锁方法与加锁方法
	public static void allTest2() {
		// 在我本机结果是乱序执行
		final SynchronizedTest st = new SynchronizedTest();
		ExecutorService es = Executors.newCachedThreadPool();
		es.execute(new Runnable() {
			@Override
			public void run() {
				st.test(61);
			}
		});

		es.execute(new Runnable() {
			@Override
			public void run() {
				st.test1(62);
			}
		});

		es.shutdown();
	}
}
