package com.examw.wechat.domain.mgr;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.examw.wechat.domain.settings.Subject;
/**
 * 资讯文档。
 * @author yangyong.
 * @since 2014-06-19.
 */
public class Article implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,title,type,picUrl,url,description,content;
	private Date createTime;
	private Integer orderNo;
	private Subject subject;
	private Article parent;
	private Set<Article> children;
	/**
	 * 文本类型。
	 */
	public static final String MSG_TYPE_TEXT = "text";
	/**
	 * 图文类型。
	 */
	public static final String MSG_TYPE_NEWS = "news";
	/**
	 * 获取父级资讯。
	 * @return 父级资讯。
	 */
	public Article getParent() {
		return parent;
	}
	/**
	 * 设置父级资讯。
	 * @param parent
	 * 父级资讯。
	 */
	public void setParent(Article parent) {
		this.parent = parent;
	}
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
	 * 获取所属科目。
	 * @return 所属科目。
	 */
	public Subject getSubject() {
		return subject;
	}
	/**
	 * 设置所属科目。
	 * @param subject
	 * 所属科目。
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
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
	 * 获取子集合。
	 * @return 子集合。
	 */
	public Set<Article> getChildren() {
		return children;
	}
	/**
	 * 设置子集合。
	 * @param children
	 * 子集合。
	 */
	public void setChildren(Set<Article> children) {
		this.children = children;
	}
}