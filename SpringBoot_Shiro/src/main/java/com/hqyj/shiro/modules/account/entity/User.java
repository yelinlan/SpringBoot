package com.hqyj.shiro.modules.account.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "m_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 主键strategy = GenerationType.IDENTITY自增
	private int userId;
	@Column(name = "user_name")
	private String userName;
	private String password;
	private Date createDate;

	@Transient
	private List<Role> roles;

	public User() {
		super();
	}

	public User(int iserId, String userName, String password, Date createDate, List<Role> roles) {
		super();
		this.userId = iserId;
		this.userName = userName;
		this.password = password;
		this.createDate = createDate;
		this.roles = roles;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", createDate="
				+ createDate + ", roles=" + roles + "]";
	}

}
