package com.examw.wechat.domain.settings;

import java.io.Serializable;
import java.util.Set;
/**
 * 考试类别。
 * @author yangyong.
 * @since 2014-04-28.
 */
public class Catalog implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,abbr_en,abbr_cn;
	private Integer orderNo;
	private Set<Exam> exams;
	/**
	 * 获取考试类别ID。
	 * @return
	 * 考试类别ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置考试类别ID。
	 * @param id
	 * 考试类别ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取考试类别名称。
	 * @return
	 * 考试类别名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置考试类别名称。
	 * @param name
	 * 考试类别名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取英文简称。
	 * @return
	 * 英文简称。
	 */
	public String getAbbr_en() {
		return abbr_en;
	}
	/**
	 * 设置英文简称。
	 * @param abbr_en
	 * 英文简称。
	 */
	public void setAbbr_en(String abbr_en) {
		this.abbr_en = abbr_en;
	}
	/**
	 * 获取中文简称。
	 * @return
	 * 中文简称。
	 */
	public String getAbbr_cn() {
		return abbr_cn;
	}
	/**
	 * 设置中文简称。
	 * @param abbr_cn
	 * 中文简称。
	 */
	public void setAbbr_cn(String abbr_cn) {
		this.abbr_cn = abbr_cn;
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
	/**
	 * 获取考试类别下的考试集合。
	 * @return
	 * 考试集合。
	 */
	public Set<Exam> getExams() {
		return exams;
	}
	/**
	 * 设置考试类别下的考试集合。
	 * @param exams
	 * 考试集合。
	 */
	public void setExams(Set<Exam> exams) {
		this.exams = exams;
	}
}