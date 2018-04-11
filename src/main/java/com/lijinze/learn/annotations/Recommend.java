package com.lijinze.learn.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 推荐写法
 * @author Aaron
 *
 */
@Retention(SOURCE)
@Target({ TYPE, FIELD, METHOD, PARAMETER, LOCAL_VARIABLE })
public @interface Recommend {
	String value() default "";
}
