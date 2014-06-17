package com.examw.wechat.domain.settings;

import java.io.Serializable;
/**
 * 课程科目设置，考试下的科目
 * @author fengwei.
 * @since 2014年4月29日 上午9:56:28.
 */
public class Subject implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id,name;
	private Integer orderNo,type;
	private Exam exam;
	/**
	 * 选修类型。
	 */
	public static final int SUBJECT_ELECTIVE = 0;
	/**
	 * 必修类型。
	 */
	public static final int SUBJECT_COMPULSORY = 1;
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
	 * 获取 所属考试
	 * @return exam
	 * 所属考试
	 */
	public Exam getExam() {
		return exam;
	}
	/**
	 * 设置 所属考试
	 * @param exam
	 * 所属考试
	 */
	public void setExam(Exam exam) {
		this.exam = exam;
	}
}