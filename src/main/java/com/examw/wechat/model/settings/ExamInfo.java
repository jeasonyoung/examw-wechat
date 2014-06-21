package com.examw.wechat.model.settings;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;
import com.examw.wechat.model.IRemoteTextValue;

/**
 * 考试信息
 * @author fengwei.
 * @since 2014年4月29日 上午10:46:15.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ExamInfo extends Paging implements IRemoteTextValue {
	private static final long serialVersionUID = 1L;
	private String id,name,abbr_en,abbr_cn,description,catalogId,catalogName;
	private Integer orderNo;
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
	 * 获取 考试类别ID
	 * @return catalogId
	 * 考试类别ID
	 */
	public String getCatalogId() {
		return catalogId;
	}
	/**
	 * 设置 考试类别ID
	 * @param catalogId
	 * 考试类别ID
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	/**
	 * 获取 考试类别名称
	 * @return catalogName
	 * 考试类别名称
	 */
	public String getCatalogName() {
		return catalogName;
	}
	/**
	 * 设置 考试类别名称
	 * @param catalogName
	 * 考试类别名称
	 */
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
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
	 * 获取 描述信息
	 * @return description
	 * 描述信息
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置 描述信息
	 * @param description
	 * 描述信息
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}