package com.hqyj.shiro.modules.account.service.serviceimpl;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hqyj.shiro.common.Result;
import com.hqyj.shiro.modules.account.dao.AccountDao;
import com.hqyj.shiro.modules.account.entity.Resource;
import com.hqyj.shiro.modules.account.entity.Role;
import com.hqyj.shiro.modules.account.entity.User;
import com.hqyj.shiro.modules.account.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao accountDao;

	@Override
	public User getUserByName(String userName) {
		return accountDao.getUserByName(userName);
	}

	@Override
	public Result addUser(User user) {
		/**
		 * 用户名可能为空
		 */
		if (null == user || user.getUserName().equals("")) {
			return new Result(500, "User name or password error");
		}

		User userTemp = accountDao.getUserByName(user.getUserName());

		if (null != userTemp) {
			return new Result(500, "User name exist.");
		}

		user.setCreateDate(new Date());
		accountDao.addUser(user);
		return new Result(200, "success", user);
	}

	@Override
	public List<User> getUsers() {
		return accountDao.getUsers();
	}

	@Override
	public List<Role> getRoles() {
		return accountDao.getRoles();
	}

	@Override
	public List<Role> getRolesByUserId(int userId) {
		return accountDao.getRolesByUserId(userId);
	}

	@Override
	public Result editUser(User user) {

		if (null == user) {
			return new Result(500, "user info is null");
		}

		// 对中间表U-R删除数据
		accountDao.deletUserRoleByUserId(user.getUserId());
		// 对中间表U-R添加数据
		if (null != user.getRoles()) {
			for (Role role : user.getRoles()) {
				accountDao.addUserRole(user.getUserId(), role.getRoleId());
			}
		}
		return new Result(200, "success", user);
	}

	@Override
	public void deleteUser(int userId) {
		accountDao.deleteUser(userId);
	}

	@Override
	public Result editRole(Role role) {
		//isEmpty    isBlank  区别
	//				StringUtils.isEmpty("yyy") = false
	//				StringUtils.isEmpty("") = true
	//				StringUtils.isEmpty("   ") = false
	//				 
	//				StringUtils.isBlank("yyy") = false
	//				StringUtils.isBlank("") = true
	//				StringUtils.isBlank("   ") = true
		if (null == role ||  StringUtils.isBlank(role.getRoleName())) {
			return new Result(500, "role info is null");
		}
		
		if(role.getRoleId()>0){
			accountDao.updateRole(role);
		}else{
			accountDao.addRole(role);
		}
		
		return new Result(200,"success",role);
	}

	@Override
	public void deleteRole(int roleId) {
		accountDao.deleteRole(roleId);
	}

	@Override
	public List<Resource> getResources() {
		return accountDao.getResources();
	}

	@Override
	public List<Role> getRolesByResourceId(int resourceId) {
		return accountDao.getRolesByResourceId(resourceId);
	}

	@Override
	public Result editResource(Resource resource) {
		
		if (null == resource ||  StringUtils.isBlank(resource.getResourceName())) {
			return new Result(500, "role info is null");
		}
		
		if(resource.getResourceId()>0){
			accountDao.updateResource(resource);
		}else{
			accountDao.addResource(resource);
		}
		
		accountDao.deleteRoleResourceByResourceId(resource.getResourceId());
		
		/**
		 * 一会儿判空，一会儿判空白？怎么判。
		 */
		if(null != resource.getRoles() && !resource.getRoles().isEmpty()){
			for (Role role : resource.getRoles()) {
				accountDao.addRoleResource(role.getRoleId(),resource.getResourceId());
			}
		}
		
		return new Result(200,"success",resource);
	}

	@Override
	public void deleteResource(int resourceId) {
		accountDao.deleteResource(resourceId);
	}

	@Override
	public List<Resource> getResourcesByRoleId(int roleId) {
		return accountDao.getResourcesByRoleId(roleId);
	}
	
	
}
