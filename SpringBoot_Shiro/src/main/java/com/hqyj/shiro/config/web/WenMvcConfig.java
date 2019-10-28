package com.hqyj.shiro.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hqyj.shiro.filter.UrlFilter;
import com.hqyj.shiro.interceptor.UriInterceptor;

/**
 * web mvc相关配置类
 */
@Configuration
@AutoConfigureAfter({ WebMvcAutoConfiguration.class })
public class WenMvcConfig implements WebMvcConfigurer {
	@Autowired
	private UriInterceptor uriInterceptor;

	/**
	 * 添加过滤器
	 */
	@Bean
	public FilterRegistrationBean<UrlFilter> filterRegistrationBean() {
		FilterRegistrationBean<UrlFilter> bean = new FilterRegistrationBean<UrlFilter>();
		bean.setFilter(new UrlFilter());
		return bean;
	}

	/*
	 * 添加拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(uriInterceptor).addPathPatterns("/**");

	}

}
