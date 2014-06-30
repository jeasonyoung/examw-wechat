package com.examw.wechat.domain.security;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
/**
 * 用户。
 * @author yangyong.
 * @since 2014-05-08.
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,account,password,nickName,imgUrl,phone,qq,email;
	private Integer gender,status;
	private Date createTime;
	private Set<Role> roles;
	/**
	 * 男性。
	 */
	public static final int GENDER_MALE = 1;
	/**
	 * 女性
	 */
	public static final int GENDER_FEMALE = 2;
	/**
	 * 启用状态。
	 */
	public static final int STATUS_ENABLED = 1;
	/**
	 * 停用状态。
	 */
	public static final int STATUS_DISABLE = 0;
	/**
	 * 获取用户ID。
	 * @return 用户ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置用户ID。
	 * @param id
	 * 用户ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取用户姓名。
	 * @return
	 * 用户姓名。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置用户姓名。
	 * @param name
	 * 用户姓名。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取用户昵称。
	 * @return
	 * 用户昵称。
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * 设置用户昵称。
	 * @param nickName
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * 获取用户账号。
	 * @return
	 * 用户账号。
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置用户账号。
	 * @param account
	 * 用户账号。
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取所属角色。
	 * @return 所属角色。
	 */
	public Set<Role> getRoles() {
		return roles;
	}
	/**
	 * 设置所属角色。
	 * @param roles
	 * 所属角色。
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	/**
	 * 获取密文密码。
	 * @return
	 * 	密文密码。
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置密文密码。
	 * @param password
	 * 密文密码。
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取性别。
	 * @return
	 * 性别(1-男，2-女)。
	 */
	public Integer getGender() {
		return gender;
	}
	/**
	 * 设置性别(1-男，2-女)。
	 * @param gender
	 * 性别(1-男，2-女)。
	 */
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	/**
	 * 获取手机号码。
	 * @return
	 * 手机号码。
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置手机号码。
	 * @param phone
	 * 手机号码。
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取QQ。
	 * @return
	 * QQ。
	 */
	public String getQq() {
		return qq;
	}
	/**
	 * 设置QQ。
	 * @param qq
	 * QQ。
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}
	/**
	 * 获取Email。
	 * @return
	 * Email。
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置Email。
	 * @param email
	 * Email。
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 设置用户头像图片URL。
	 * @return
	 * 用户图片URL。
	 */
	public String getImgUrl() {
		return imgUrl;
	}
	/**
	 * 设置用户头像图片URL。
	 * @param imgUrl
	 * 用户图片URL。
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	/**
	 * 获取用户状态。
	 * @return
	 * 用户状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置用户状态。
	 * @param status
	 * 用户状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取用户创建时间。
	 * @return
	 * 用户创建时间。
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置用户创建时间。
	 * @param createTime
	 * 用户创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}