package com.hqyj.shiro.modules.account.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "m_role_resource")
public class RoleResource {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roleResourceId;
	private int roleId;
	private int resourceId;
	public RoleResource() {
		super();
	}
	public RoleResource(int roleResourceId, int roleId, int resourceId) {
		super();
		this.roleResourceId = roleResourceId;
		this.roleId = roleId;
		this.resourceId = resourceId;
	}
	public int getRoleResourceId() {
		return roleResourceId;
	}
	public void setRoleResourceId(int roleResourceId) {
		this.roleResourceId = roleResourceId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	@Override
	public String toString() {
		return "RoleResource [roleResourceId=" + roleResourceId + ", roleId=" + roleId + ", resourceId=" + resourceId
				+ "]";
	}
	
}
