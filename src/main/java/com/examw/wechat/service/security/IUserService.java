package com.examw.wechat.service.security;

import java.util.Set;

import com.examw.wechat.domain.security.User;
import com.examw.wechat.model.security.UserInfo;
import com.examw.wechat.service.IBaseDataService;

/**
 * 用户服务接口。
 * @author yangyong.
 * @since 2014-05-08.
 */
public interface IUserService extends IBaseDataService<UserInfo> {
	/**
	 * 加载用户状态名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadUserStatusName(Integer status);
	/**
	 * 加载性别名称。
	 * @param gender
	 * 性别值。
	 * @return
	 * 性别名称。
	 */
	String loadGenderName(Integer gender);
	/**
	 * 修改密码。
	 * @param userId
	 * 用户ID。
	 * @param newPassword
	 * 新密码。
	 */
	void changePassword(String userId, String newPassword);
	/**
	 * 根据账号查找用户。
	 * @param account
	 * 用户账号。
	 * @return
	 * 用户信息。
	 */
	User findByAccount(String account);
	/**
	 * 根据账号查找用户角色。
	 * @param account
	 * 用户账号。
	 * @return
	 * 角色ID集合。
	 */
	Set<String> findRoles(String account);
	/**
	 * 根据账号查询其权限。
	 * @param account
	 * 用户账号。
	 * @return
	 * 权限集合。
	 */
	Set<String> findPermissions(String account);
}