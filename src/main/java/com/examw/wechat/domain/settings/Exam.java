package com.examw.wechat.domain.settings;

import java.io.Serializable;
import java.util.Set;
/**
 * 考试设置.
 * @author fengwei.
 * @since 2014年4月29日 上午10:27:48.
 */
public class Exam implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,abbr_en,abbr_cn,description;
	private Integer orderNo;
	private Catalog catalog;
	private Set<Subject> subjects;
	/**
	 * 获取 考试ID
	 * @return id
	 * 考试ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 考试ID
	 * @param id
	 * 考试ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 考试名称
	 * @return name
	 * 考试名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置 考试名称
	 * @param name
	 * 考试名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取 考试描述
	 * @return description
	 * 考试描述
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置 考试描述
	 * @param description
	 * 考试描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取 排序号
	 * @return orderNo
	 * 排序号
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置 排序号
	 * @param orderNo
	 * 排序号
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取所属考试类别
	 * @return catalog
	 * 考试类别
	 */
	public Catalog getCatalog() {
		return catalog;
	}
	/**
	 * 设置 考试类别
	 * @param catalog
	 * 考试类别
	 */
	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}
	/**
	 * 获取 英文简称
	 * @return abbr_en
	 * 英文简称
	 */
	public String getAbbr_en() {
		return abbr_en;
	}
	/**
	 * 设置 英文简称
	 * @param abbr_en
	 * 英文简称
	 */
	public void setAbbr_en(String abbr_en) {
		this.abbr_en = abbr_en;
	}
	/**
	 * 获取 中文简称
	 * @return abbr_cn
	 * 中文简称
	 */
	public String getAbbr_cn() {
		return abbr_cn;
	}
	/**
	 * 设置 中文简称
	 * @param abbr_cn
	 * 中文简称
	 */
	public void setAbbr_cn(String abbr_cn) {
		this.abbr_cn = abbr_cn;
	}
	/**
	 * 获取 考试科目集合
	 * @return subjects
	 * 
	 */
	public Set<Subject> getSubjects() {
		return subjects;
	}
	/**
	 * 设置 考试科目集合
	 * @param subjects
	 * 
	 */
	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}
}