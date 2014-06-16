package com.examw.wechat.model.security;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;

/**
 * 角色信息。
 * @author yangyong.
 * @since 2014-05-05.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class RoleInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,name,statusName,description;
	private Integer status;
	/**
	 * 获取角色ID。
	 * @return
	 * 角色ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置角色ID。
	 * @param id
	 * 角色ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取角色名称。
	 * @return
	 * 角色名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置角色名称。
	 * @param name
	 * 角色名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取描述信息。
	 * @return
	 * 描述信息。
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置描述信息。
	 * @param description
	 * 描述信息。
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取状态。
	 * @return
	 * 状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置状态。
	 * @param status
	 * 状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取状态名称。
	 * @return
	 * 状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置状态名称。
	 * @param statusName
	 * 状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}