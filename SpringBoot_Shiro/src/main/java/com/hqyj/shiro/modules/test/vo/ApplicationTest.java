package com.hqyj.shiro.modules.test.vo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * @author Administrator
 *可以读取其他配置文件的内容
 */
@Component
@PropertySource("classpath:config/applicationTest.properties")
@ConfigurationProperties(prefix="com.hqyj1")
public class ApplicationTest {
	private int port;
	private String name;
	private	int age;
	private	String description;
	private	String random;
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRandom() {
		return random;
	}
	public void setRandom(String random) {
		this.random = random;
	}
	
	
	
}
