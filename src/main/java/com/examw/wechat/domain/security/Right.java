package com.examw.wechat.domain.security;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 基础权限。
 * @author yangyong.
 * @since 2014-05-03.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class Right implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id,name;
	private int value,orderNo;
	/**
	 *  查看数据权限。
	 */
	public static final int VIEW = 1;
	/**
	 * 更新数据权限。
	 */
	public static final int UPDATE = 2;
	/**
	 * 删除数据权限。
	 */
	public static final int DELETE = 3;
	/**
	 * 获取权限ID。
	 * @return
	 * 权限ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置权限ID。
	 * @param id
	 * 权限ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取权限名称。
	 * @return
	 * 权限名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置权限名称。
	 * @param name
	 * 权限名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取权限值。
	 * @return
	 * 权限值。
	 */
	public int getValue() {
		return value;
	}
	/**
	 * 设置权限值。
	 * @param value
	 * 权限值。
	 */
	public void setValue(int value) {
		this.value = value;
	}
	/**
	 * 获取排序号。
	 * @return
	 * 排序号。
	 */
	public int getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序号。
	 * @param orderNo
	 * 排序号。
	 */
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
}