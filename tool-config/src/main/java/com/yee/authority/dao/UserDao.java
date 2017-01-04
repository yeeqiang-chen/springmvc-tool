package com.yee.authority.dao;


import com.yee.authority.vo.ResourceVo;
import com.yee.authority.vo.UserVo;

import java.util.List;
import java.util.Map;

public interface UserDao {

	/**
	 * 根据用户登录名得到用户
	 * @param userAccount
	 * @return
	 */
	UserVo getUserByUsername(String userAccount);

	/**
	 * 得到所有的资源
	 * @return
	 */
	List<ResourceVo> getAllResource();
	
	/**
	 * 根据用户登录名得到用户拥有的所有角色
	 * @param userAccount
	 * @return
	 */
	List<String> getUserRolesByUserAccount(String userAccount);

	/**
	 * 获取一个用户拥有的所有资源
	 * @param map
	 * @return
	 */
	List<ResourceVo> getUserResources(Map<String, Object> map);

	/**
	 * 去重用户所有资源
	 * @param map
	 * @return
	 */
	List<ResourceVo> getUserResourcesMenu(Map<String, Object> map);
	
	/**
	 * 禁用用户信息 
	 * @param userAccount
	 * @return
	 */
	void updateUserStatus(String userAccount);
}
