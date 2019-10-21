package com.hqyj.shiro.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 自定义拦截器，实现modelMap中不指定template，自动按照uri进行装配的功能
 * 实现步骤：继承HandlerInterceptor ---- 注册为组件 ---- 重写postHandle方法 ---- 配置类中注册addInterceptors
 */
@Component
public class UriInterceptor implements HandlerInterceptor{

	/* 
	 * 拦截点之前执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("start interceptor.------------------");
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	/* 
	 * 拦截点执行
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		String uri = request.getServletPath();
		if(null == modelAndView || modelAndView.getViewName().startsWith("redirect")){
			return;
		}
		if(modelAndView.getModelMap().get("template") == null){
			if(uri.startsWith("/")){
				 uri = uri.substring(1);
				 System.err.println(uri+"------------------");
			}
			modelAndView.addObject("template",uri.toLowerCase());
		}
	}

	/* 
	 * 拦截点之后执行
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("End interceptor.------------------");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
