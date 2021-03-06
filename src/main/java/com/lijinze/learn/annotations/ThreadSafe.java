/**
 *  @描述：
 *  
 *  版本    作者        时间           邮箱                描述
 * =============================================================
 * v1.0   李金泽   2018年4月11日  lijinze@beixing360.com     初版
 * =============================================================
 * */
package com.lijinze.learn.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(SOURCE)
@Target({ TYPE, FIELD, METHOD, PARAMETER, LOCAL_VARIABLE })
/**
 * 线程安全的
 * 
 * @author Aaron
 *
 */
public @interface ThreadSafe {
	String value() default "";
}
