package com.hqyj.shiro.modules.account.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "m_role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roleId;
	private String roleName;
	
	@Transient
	private List<User> users;
	@Transient
	private List<Resource> resource;
	
	public Role() {
		super();
	}
	public Role(int roleId, String roleName, List<User> users, List<Resource> resource) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.users = users;
		this.resource = resource;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public List<Resource> getResource() {
		return resource;
	}
	public void setResource(List<Resource> resource) {
		this.resource = resource;
	}
	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName=" + roleName + ", users=" + users + ", resource=" + resource
				+ "]";
	}
	
	
}
