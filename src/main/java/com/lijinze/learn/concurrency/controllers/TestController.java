/**
 *  @描述：
 *  
 *  版本    作者        时间           邮箱                描述
 * =============================================================
 * v1.0   李金泽   2018年4月11日  lijinze@beixing360.com     初版
 * =============================================================
 * */
package com.lijinze.learn.concurrency.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Aaron
 *
 */
@Slf4j
@Controller
public class TestController {

	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		log.info("hello ");
		return "hello";
	}
}
