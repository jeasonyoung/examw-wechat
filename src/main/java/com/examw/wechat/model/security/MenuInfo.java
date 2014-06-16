package com.examw.wechat.model.security;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
/**
 * 菜单信息。
 * @author yangyong.
 * @since 2014-04-28.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class MenuInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String pid, id,icon,name,uri;
	private int orderNo;
	private List<MenuInfo> children;
	/**
	 * 获取上级菜单ID。
	 * @return
	 * 上级菜单ID。
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置上级菜单ID。
	 * @param pid
	 * 上级菜单ID。
	 */
	public void setPid(String pid) {
		this.pid = pid;
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
	 *  子菜单集合。
	 */
	public List<MenuInfo> getChildren() {
		return children;
	}
	/**
	 * 设置子菜单集合。
	 * @param children
	 * 子菜单集合。
	 */
	public void setChildren(List<MenuInfo> children) {
		this.children = children;
	}
}