package com.examw.wechat.model.security;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
/**
 * 基础权限信息。
 * @author yangyong.
 * @since 2014-05-03.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class RightInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,name;
	private Integer value,orderNo;
	/**
	 * 构造函数。
	 */
	public RightInfo(){}
	/**
	 * 构造函数。
	 * @param id
	 * 权限ID。
	 * @param name
	 * 权限名称。
	 * @param value
	 * 权限值。
	 */
	public RightInfo(String id, String name,Integer value){
		this.setId(id);
		this.setName(name);
		this.setValue(value);
		this.setOrderNo(value);
	}
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
	public Integer getValue() {
		return value;
	}
	/**
	 * 设置权限值。
	 * @param value
	 * 权限值。
	 */
	public void setValue(Integer value) {
		this.value = value;
	}
	/**
	 * 获取排序号。
	 * @return
	 * 排序号。
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序号。
	 * @param orderNo
	 * 排序号。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
}