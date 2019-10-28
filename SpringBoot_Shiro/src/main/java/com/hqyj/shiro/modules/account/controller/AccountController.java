package com.hqyj.shiro.modules.account.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hqyj.shiro.common.Result;
import com.hqyj.shiro.modules.account.entity.Resource;
import com.hqyj.shiro.modules.account.entity.Role;
import com.hqyj.shiro.modules.account.entity.User;
import com.hqyj.shiro.modules.account.service.AccountService;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@RequestMapping("/login")
	public String loginPage(ModelMap modelMap) {
		modelMap.addAttribute("template", "account/login");
		return "indexSimple";
	}

	/**
	 * 登录
	 * 
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Result doLogin(@RequestBody User user, HttpServletRequest request) {
		Result result = new Result(200, "success");

		try {
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
			// 调用认证
			subject.login(token);
			// 调用授权
			subject.checkRoles();
		} catch (AuthenticationException e) {
			result = new Result(500, e.getMessage());
			return result;
		}
		/**
		 * request.getSession(true)：若存在会话则返回该会话，否则新建一个会话。
		 * request.getSession(false)：若存在会话则返回该会话，否则返回NULL
		 */
		HttpSession session = request.getSession(true);
		session.setAttribute("user", user);
		return result;
	}

	@RequestMapping("/dashboard")
	public String dashboardPage(ModelMap modelMap) {
		modelMap.addAttribute("template", "account/dashboard");
		return "index";
	}

	@RequestMapping("/logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "redirect:/account/login";
	}

	@RequestMapping("/register")
	public String registerPage(ModelMap modelMap) {
		modelMap.addAttribute("template", "/account/register");// 键不能为空
		return "/indexSimple";
	}

	/**
	 * 注册
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/doRegister", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Result doRegister(HttpServletRequest request, @RequestBody User user) {
		Result result = accountService.addUser(user);

		HttpSession session = request.getSession(true);
		session.setAttribute("user", user);
		return result;
	}

	/**
	 * 显示数据，用户，角色
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/users")
	@RequiresRoles(value ={"admin","manager"},logical = Logical.OR )
	public String usersPage(ModelMap modelMap) {
		modelMap.put("users", accountService.getUsers());
		modelMap.put("roles", accountService.getRoles());// 键可以为空

		modelMap.put("template", "/account/users");
		return "index";
	}

	/**
	 * 勾角色
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/roles/user/{userId}")
	@ResponseBody
	public List<Role> getRolesByUserId(@PathVariable("userId") int userId) {
		return accountService.getRolesByUserId(userId);
	}

	@RequestMapping(value = "/editUser", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	/**
	 * 改角色
	 * 
	 * @param user
	 * @return
	 */
	public Result editUser(@RequestBody User user) {
		return accountService.editUser(user);
	}

	/**
	 * 删除用户
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/deleteUser/{userId}")
	 @RequiresPermissions("deleteUser")
	public String deleteUser(@PathVariable("userId") int userId) {
		accountService.deleteUser(userId);
		return "redirect:/account/users";
	}

	@RequestMapping(value = "/roles")
	public String rolesPage(ModelMap modelMap) {
		modelMap.put("roles", accountService.getRoles());
		modelMap.addAttribute("template", "/account/roles");
		return "index";
	}

	/**
	 * 删除user shiro常见注解
	 * 
	 * @RequiresAuthentication : 表示当前 Subject 已经认证登录的用户才能调用的代码块。
	 * @RequiresUser : 表示当前 Subject 已经身份验证或通过记住我登录的。
	 * @RequiresGuest : 表示当前 Subject
	 *                没有身份验证，即是游客身份。 @RequiresRoles(value={"admin", "user"},
	 *                logical=Logical.AND)
	 * @RequiresPermissions (value={"***","***"}, logical= Logical.OR)
	 */

	@RequestMapping(value = "/editRole", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Result editRole(@RequestBody Role role) {
		return accountService.editRole(role);
	}

	@RequestMapping("/deleteRole/{roleId}")
	public String deleteRole(@PathVariable("roleId") int roleId) {
		accountService.deleteRole(roleId);
		return "redirect:/account/roles";

	}

	@RequestMapping(value = "/resources")
	public String resourcesPage(ModelMap modelMap) {
		modelMap.put("resources", accountService.getResources());
		modelMap.put("roles", accountService.getRoles());// 键可以为空
		modelMap.addAttribute("template", "/account/resources");
		return "index";
	}

	@RequestMapping(value = "/roles/resource/{resourceId}")
	@ResponseBody
	public List<Role> getRolesByResourceId(@PathVariable("resourceId") int resourceId) {
		return accountService.getRolesByResourceId(resourceId);
	}

	@RequestMapping(value = "/editResource", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Result editResource(@RequestBody Resource resource) {

		return accountService.editResource(resource);
	}

	@RequestMapping(value = "/deleteResource/{resourceId}")
	public String deleteResource(@PathVariable("resourceId") int resourceId, ModelMap modelMap) {
		accountService.deleteResource(resourceId);
		// 这是重定向吗？
		return "redirect:/account/resources";

	}
}
