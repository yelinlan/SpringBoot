package com.hqyj.shiro.config.shiro;

import java.util.LinkedHashMap;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ShiroConfig {

	@Autowired
	private MyRealm myRealm;
	
	@Bean
	public DefaultWebSecurityManager securityManager() {
			DefaultWebSecurityManager securityManager = new 
					DefaultWebSecurityManager(myRealm);
			
			return securityManager;
	}
	/**
	 * 配置shiro过滤器工厂
	 * -----------------
	 * 拦截权限
	 * anon：匿名访问，无需登录
	 * authc：登录后才能访问
	 * logout：登出
	 * roles：角色过滤器
	 * ------------------
	 * URL匹配风格
	 * ?：匹配一个字符，如 /admin? 将匹配 /admin1，但不匹配 /admin 或 /admin/
	 * *：匹配零个或多个字符串，如 /admin* 将匹配 /admin 或/admin123，但不匹配 /admin/1
	 * **：匹配路径中的零个或多个路径，如 /admin/** 将匹配 /admin/a 或 /admin/a/b
	 * -----------------------
	 * 方法名不能乱写，如果我们定义为别的名称，又没有添加注册过滤器的配置，那么shiro会加载ShiroWebFilterConfiguration过滤器，
	 * 该过滤器会寻找shiroFilterFactoryBean，找不到会抛出异常
	 */
	
	//核心过滤器
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(){
		ShiroFilterFactoryBean  filterFactory= new ShiroFilterFactoryBean();
		filterFactory.setSecurityManager(securityManager());
		
		filterFactory.setLoginUrl("/account/login");
		filterFactory.setSuccessUrl("account/dashboard");
		filterFactory.setUnauthorizedUrl("/error/403");

		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		
		map.put("/static/**", "anon");//下目录被访问
		map.put("/css/**", "anon");//下文件被访问
		map.put("/js/**", "anon");
		map.put("/image/**", "anon");
		map.put("/test/**", "anon");
		map.put("/account/doLogin", "anon");
		map.put("/account/register", "anon");
		map.put("/account/doRegister", "anon");
		map.put("/account/**", "authc");
		
		filterFactory.setFilterChainDefinitionMap(map);
		
		return filterFactory;
	}
	
	@Bean
	public ShiroDialect shiroDialect(){
		return new ShiroDialect();
	}
	
	@Bean
	@DependsOn({"lifecycleBeanPostProcessor"})
	DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator= new 
				DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}
	
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new
				AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
		return authorizationAttributeSourceAdvisor;
	}
}
