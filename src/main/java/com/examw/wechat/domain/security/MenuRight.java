package com.examw.wechat.domain.security;

import java.io.Serializable;
import java.util.Set;

/**
 * 菜单权限。
 * @author yangyong.
 * @since 2014-05-03.
 */
public class MenuRight implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,code;
	private Menu menu;
	private Right right;
	private Set<Role> roles;
	/**
	 * 获取菜单权限ID。
	 * @return
	 * 菜单权限ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置菜单权限ID。
	 * @param id
	 * 菜单权限ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取菜单权限代码。
	 * @return
	 * 菜单权限代码。
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置菜单权限代码。
	 * @param code
	 * 菜单权限代码。
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取菜单权限所属角色集合。
	 * @return
	 * 所属角色集合。
	 */
	public Set<Role> getRoles() {
		return roles;
	}
	/**
	 * 设置菜单权限所属角色集合。
	 * @param roles
	 * 所属角色集合。
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	/**
	 * 获取所属菜单。
	 * @return
	 * 所属菜单。
	 */
	public Menu getMenu() {
		return menu;
	}
	/**
	 * 设置所属菜单。
	 * @param menu
	 * 所属菜单。
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	/**
	 * 获取所属权限。
	 * @return
	 * 所属权限。
	 */
	public Right getRight() {
		return right;
	}
	/**
	 * 设置所属权限。
	 * @param right
	 * 所属权限。
	 */
	public void setRight(Right right) {
		this.right = right;
	}
}