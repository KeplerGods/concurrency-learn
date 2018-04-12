/**
 *  @描述：
 *  
 *  版本    作者        时间           邮箱                描述
 * =============================================================
 * v1.0   李金泽   2018年4月12日  lijinze@beixing360.com     初版
 * =============================================================
 * */
package com.lijinze.learn.concurrency.test;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Aaron
 *
 */
@Slf4j
public class AtomicIntegerFieldUpdaterTest {
	private static AtomicIntegerFieldUpdater<AtomicIntegerFieldUpdaterTest> a = AtomicIntegerFieldUpdater
			.newUpdater(AtomicIntegerFieldUpdaterTest.class, "value");
	// 变量必须是 int 基本类型，不能是对象类型
	// 变量必须有 volatile 关键字修饰
	// 以下是源码中的判断
	// if (field.getType() != int.class)
	// throw new IllegalArgumentException("Must be integer type");
	//
	// if (!Modifier.isVolatile(modifiers))
	// throw new IllegalArgumentException("Must be volatile type");
	@Getter
	// 未初始化默认是0
	private volatile int value;

	private static AtomicIntegerFieldUpdaterTest aifu = new AtomicIntegerFieldUpdaterTest();

	public static void main(String[] args) {
		// 比较设置
		a.compareAndSet(aifu, 0, 2);
		log.info("{}", aifu.getValue());
	}
}
