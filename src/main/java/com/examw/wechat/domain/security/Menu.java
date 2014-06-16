package com.examw.wechat.domain.security;

import java.io.Serializable;
import java.util.Set;

/**
 * 菜单数据
 * @author yangyong.
 * @since 2014-04-28.
 */
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,icon,name,uri;
	private int orderNo;
	private Menu parent;
	private Set<Menu> children;
	/**
	 * 获取上级菜单。
	 * @return
	 * 	上级菜单。
	 */
	public Menu getParent() {
		return parent;
	}
	/**
	 * 设置上级菜单。
	 * @param parent
	 * 上级菜单。
	 */
	public void setParent(Menu parent) {
		this.parent = parent;
	}
	/**
	 * 获取菜单ID。
	 * @return
	 * 菜单ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置菜单ID。
	 * @param id
	 * 菜单ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取菜单图标。
	 * @return
	 * 菜单图标。
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * 设置菜单图标。
	 * @param icon
	 * 菜单图标。
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * 获取菜单名称。
	 * @return
	 * 菜单名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置菜单名称。
	 * @param name
	 * 菜单名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取菜单Uri。
	 * @return
	 * 菜单Uri。
	 */
	public String getUri() {
		return uri;
	}
	/**
	 * 设置菜单URL。
	 * @param uri
	 * 菜单URL。
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}
	/**
	 * 获取排序。
	 * @return
	 * 排序。
	 */
	public int getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序。
	 * @param orderNo
	 * 排序。
	 */
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取子菜单集合。
	 * @return
	 * 子菜单集合。
	 */
	public Set<Menu> getChildren() {
		return children;
	}
	/**
	 * 设置子菜单集合。
	 * @param children
	 * 子菜单集合。
	 */
	public void setChildren(Set<Menu> children) {
		this.children = children;
	}
}