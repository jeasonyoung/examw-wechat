package com.examw.wechat.model.settings;

import com.examw.model.Paging;

/**
 * 省份信息。
 * @author yangyong.
 * @since 2014-06-20.
 */
public class ProvinceInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,code,name;
	private Integer orderNo;
	/**
	 * 获取省份ID。
	 * @return 省份ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置省份ID。
	 * @param id
	 * 省份ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取省份代码。
	 * @return 省份代码。
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置省份代码。
	 * @param code
	 * 省份代码。
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取省份名称。
	 * @return 省份名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置省份名称。
	 * @param name
	 * 省份名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取排序号。
	 * @return 排序号。
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