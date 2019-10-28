package com.hqyj.shiro.modules.account.service;

import java.util.List;

import com.hqyj.shiro.common.Result;
import com.hqyj.shiro.modules.account.entity.Resource;
import com.hqyj.shiro.modules.account.entity.Role;
import com.hqyj.shiro.modules.account.entity.User;

public interface AccountService {

	User getUserByName(String userName);

	Result addUser(User user);

	List<User> getUsers();

	List<Role> getRoles();

	List<Role> getRolesByUserId(int userId);

	Result editUser(User user);

	void deleteUser(int userId);

	Result editRole(Role role);

	void deleteRole(int roleId);

	List<Resource> getResources();

	List<Role> getRolesByResourceId(int resourceId);

	Result editResource(Resource resource);

	void deleteResource(int resourceId);

	List<Resource> getResourcesByRoleId(int roleId);


}
	