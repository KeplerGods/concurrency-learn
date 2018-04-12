package com.lijinze.learn.concurrency.test;

import java.util.concurrent.atomic.AtomicReference;

import com.lijinze.learn.annotations.ThreadSafe;

import lombok.extern.slf4j.Slf4j;

@ThreadSafe
@Slf4j
public class AtomicReferenceTest {

	private static AtomicReference<Integer> ar = new AtomicReference<Integer>(0);

	public static void main(String[] args) {
		// 比较更新方法，如果是值是0，则更新为1
		log.info("{} -> {} - {}", 0, 1, ar.compareAndSet(0, 1));
		// 获取原先的值，并设置为指定的新值
		log.info("{} -> {}", ar.getAndSet(3), ar.get());
		
		// 以下是源码实现，核心还是使用的Unsafe类的方法
		// /**
		// * Atomically sets the value to the given updated value
		// * if the current value {@code ==} the expected value.
		// * @param expect the expected value
		// * @param update the new value
		// * @return {@code true} if successful. False return indicates that
		// * the actual value was not equal to the expected value.
		// */
		// public final boolean compareAndSet(V expect, V update) {
		// native 原子方法
		// return unsafe.compareAndSwapObject(this, valueOffset, expect,
		// update);
		// }
	}
}
