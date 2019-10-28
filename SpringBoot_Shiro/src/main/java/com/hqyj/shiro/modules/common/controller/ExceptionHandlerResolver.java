package com.hqyj.shiro.modules.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 异常处理类
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerResolver {

	/**
	 * 集中处理 controller 层 AuthorizationException 异常
	 * 用户登录情况下访问被保护资源 ---- 403错误码
	 */
	@ExceptionHandler(value =AuthorizationException.class )
	public String handlerAccessDeniedException(HttpServletRequest reuqest, 
			AuthorizationException exception){
		return "redirect:/error/403";
	}
}
