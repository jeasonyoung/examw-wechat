package com.examw.wechat.model.security;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;

/**
 * 菜单权限信息。
 * @author yangyong.
 * @since 2014-05-04
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class MenuRightInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,menuId,menuName,rightId,rightName,code;
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
	 * 获取所属菜单ID。
	 * @return
	 * 所属菜单ID。
	 */
	public String getMenuId() {
		return menuId;
	}
	/**
	 * 设置所属菜单ID。
	 * @param menuId
	 * 所属菜单ID。
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	/**
	 * 获取所属菜单名称。
	 * @return
	 * 所属菜单名称。
	 */
	public String getMenuName() {
		return menuName;
	}
	/**
	 * 设置所属菜单名称。
	 * @param menuName
	 * 所属菜单名称。
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	/**
	 * 获取所属权限ID。
	 * @return
	 * 所属权限ID。
	 */
	public String getRightId() {
		return rightId;
	}
	/**
	 * 设置所属权限ID。
	 * @param rightId
	 * 所属权限ID。
	 */
	public void setRightId(String rightId) {
		this.rightId = rightId;
	}
	/**
	 * 获取所属权限名称。
	 * @return
	 * 所属权限名称。
	 */
	public String getRightName() {
		return rightName;
	}
	/**
	 * 设置所属权限名称。
	 * @param rightName
	 * 所属权限名称。
	 */
	public void setRightName(String rightName) {
		this.rightName = rightName;
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
}