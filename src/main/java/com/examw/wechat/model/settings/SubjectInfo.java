package com.examw.wechat.model.settings;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;

/**
 * 科目信息
 * @author fengwei.
 * @since 2014年4月29日 上午11:04:56.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SubjectInfo extends Paging implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,catalogId,catalogName,examId,examName, typeName;
	private Integer type,orderNo;
	/**
	 * 获取 科目ID
	 * @return id
	 * 科目ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 科目ID
	 * @param id
	 * 科目ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 科目名称
	 * @return name
	 * 科目名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置 科目名称
	 * @param name
	 * 科目名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取所属考试类别ID。
	 * @return
	 * 所属考试类别。
	 */
	public String getCatalogId() {
		return catalogId;
	}
	/**
	 * 设置所属考试类别ID。
	 * @param catalogId
	 * 所属考试类别ID。
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	/**
	 * 获取所属考试类别名称。
	 * @return
	 * 所属考试类别名称。
	 */
	public String getCatalogName() {
		return catalogName;
	}
	/**
	 * 设置所属考试类别名称。
	 * @param catalogName
	 * 所属考试类别名称。
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
	 * 获取 科目类型 【0-选修，1-必修】
	 * @return type
	 * 科目类型
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置 科目类型
	 * @param type
	 * 科目类型
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取 所属考试ID
	 * @return examId
	 * 所属考试ID
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * 设置 所属考试ID
	 * @param examId
	 * 所属考试ID
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * 获取 所属考试名称
	 * @return examName
	 * 所属考试名称
	 */
	public String getExamName() {
		return examName;
	}
	/**
	 * 设置 所属考试名称
	 * @param examName
	 * 所属考试名称
	 */
	public void setExamName(String examName) {
		this.examName = examName;
	}
	/**
	 * 获取 类型名称[选修必修]
	 * @return typeName
	 * 类型名称
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置 类型名称
	 * @param typeName
	 * 类型名称
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}