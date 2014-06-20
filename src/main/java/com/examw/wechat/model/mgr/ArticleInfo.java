package com.examw.wechat.model.mgr;

import java.util.Date;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;

/**
 * 资讯文档信息。
 * @author yangyong.
 * @since 2014-06-19.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ArticleInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,title,type,picUrl,url,description,content,examId,examName;
	private Date createTime;
	private Integer orderNo;
	private Set<ArticleInfo> children;
	/**
	 * 获取资讯ID。
	 * @return 资讯ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置资讯ID。
	 * @param id
	 * 资讯ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取资讯标题。
	 * @return 资讯标题。
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置资讯标题。
	 * @param title 
	 * 资讯标题。
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取资讯类型。
	 * @return 资讯类型。
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置资讯类型。
	 * @param type
	 * 资讯类型。
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取所属科目ID。
	 * @return 所属科目ID。
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * 设置所属科目ID。
	 * @param examId
	 * 所属科目ID。
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * 获取所属科目名称。
	 * @return 所属科目名称。
	 */
	public String getExamName() {
		return examName;
	}
	/**
	 * 设置所属科目名称。
	 * @param examName
	 * 所属科目名称。
	 */
	public void setExamName(String examName) {
		this.examName = examName;
	}
	/**
	 * 获取图片链接。
	 * @return 图片链接。
	 */
	public String getPicUrl() {
		return picUrl;
	}
	/**
	 * 设置图片链接。
	 * @param picUrl
	 * 图片链接。
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	/**
	 * 获取图文消息跳转链接。
	 * @return 图文消息跳转链接。
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置图文消息跳转链接。
	 * @param url
	 * 图文消息跳转链接。
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取描述信息。
	 * @return 描述信息。
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
	 * 获取内容。
	 * @return 内容。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置内容。
	 * @param content
	 * 内容。
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间。
	 * @param createTime
	 * 创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	/**
	 * 获取子资讯文档集合。
	 * @return 子资讯文档集合。
	 */
	public Set<ArticleInfo> getChildren() {
		return children;
	}
	/**
	 * 设置子资讯文档集合。
	 * @param children
	 * 子资讯文档集合。
	 */
	public void setChildren(Set<ArticleInfo> children) {
		this.children = children;
	}
}