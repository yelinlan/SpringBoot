package com.hqyj.shiro.modules.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.hqyj.shiro.common.Result;
import com.hqyj.shiro.modules.account.entity.Resource;
import com.hqyj.shiro.modules.account.entity.Role;
import com.hqyj.shiro.modules.account.entity.User;

@Repository
@Mapper
public interface AccountDao {

	@Select("SELECT * FROM m_user WHERE user_name=#{userName}")
	User getUserByName(String userName);

	@Insert("INSERT INTO  m_user(user_name,password,create_date)"
			+ " VALUES(#{userName},#{password},#{createDate})")
	@Options(useGeneratedKeys  = true,keyProperty = "userId",keyColumn = "user_id")
	void addUser(User user);

	@Select("SELECT * FROM m_user ")
	List<User> getUsers();

	@Select("SELECT * FROM m_role")
	List<Role> getRoles();

	//left   why?
	@Select("SELECT * FROM m_role AS mr left join m_user_role AS mur on mr.role_id=mur.role_id WHERE mur.user_id=#{userId}")
	List<Role> getRolesByUserId(int userId);

	@Delete("DELETE FROM m_user_role WHERE m_user_role.user_id=#{userId}")
	void deletUserRoleByUserId(int userId);

	//	@Param是MyBatis所提供的(org.apache.ibatis.annotations.Param)，
	//	作为Dao层的注解，作用是用于传递参数，从而可以与SQL中的的字段名相对应，
	//	一般在2=<参数数<=5时使用最佳。
	@Insert("INSERT INTO m_user_role(user_id,role_id) VALUES(#{userId},#{roleId})")
	void addUserRole(@Param("userId") int userId,@Param("roleId") int roleId);

	@Delete("DELETE FROM m_user WHERE user_id=#{userId}")
	void deleteUser(int userId);

	@Delete("DELETE FROM m_role WHERE role_id=#{roleId}")
	void deletRoleByRoleId(int roleId);

	//value 与 values区别  		values（，，，），（，，，）可以插入多条数据
	@Insert("INSERT INTO m_role(role_name) VALUES(#{roleName})")
	void addRole(Role role);

	@Update("UPDATE m_role set role_name=#{roleName} WHERE role_id=#{roleId}")
	void updateRole(Role role);

	@Delete("DELETE FROM m_role WHERE role_id=#{roleId}")
	void deleteRole(int roleId);

	@Select("SELECT * FROM m_resource ")
	List<Resource> getResources();

	@Select("SELECT * FROM m_role AS mr join m_role_resource AS mrr on mr.role_id=mrr.role_id WHERE mrr.resource_id=#{resourceId}")
	List<Role> getRolesByResourceId(int resourceId);

	@Update("UPDATE m_resource set resource_name= #{resourceName},resource_uri=#{resourceUri},"
			+ "permission=#{permission} WHERE resource_id=#{resourceId}")
	void updateResource(Resource resource);

	/**
	 * 当主键是自增的情况下，添加一条记录的同时，
	 * 其主键是不能使用的，但是有时我们需要该主键，
	 * useGeneratedKeys，keyProperty，
	 * 可以返回插入的ID值
	 * @param resource
	 */
	@Insert("INSERT INTO m_resource(resource_name,resource_uri,permission) VALUES(#{resourceName},#{resourceUri},#{permission})")
	@Options(useGeneratedKeys  = true,keyProperty = "resourceId",keyColumn = "resource_id")
	void addResource(Resource resource);

	@Delete("DELETE FROM m_role_resource  WHERE resource_id = #{resourceId}")
	void deleteRoleResourceByResourceId(int resourceId);

	@Insert("INSERT INTO m_role_resource(role_id,resource_id) value(#{roleId},#{resourceId})")
	//@Options(useGeneratedKeys  = true,keyProperty = "role_resource_id",keyColumn = "roleResourceId")
	void addRoleResource(@Param("roleId") int roleId,@Param("resourceId") int resourceId);

	@Delete("DELETE FROM m_resource WHERE resource_id=#{resourceId}")
	void deleteResource(int resourceId);

	@Select("SELECT * FROM m_role_resource AS mrr join m_resource AS mre on "
			+ "mrr.resource_id=mre.resource_id WHERE mrr.role_id=#{roleId}")
	List<Resource> getResourcesByRoleId(int roleId);

}
