package com.examw.wechat.domain.mgr;

import java.io.Serializable;
import java.util.Date;

/**
 *  消息记录。
 * @author yangyong.
 * @since 2014-07-02.
 */
public class Record implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,content;
	private AccountUser accountUser;
	private Date createTime;
	/**
	 * 获取消息记录ID。
	 * @return 消息记录ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置消息记录ID。
	 * @param id
	 * 消息记录ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取所属关注用户。
	 * @return 关注用户。
	 */
	public AccountUser getAccountUser() {
		return accountUser;
	}
	/**
	 * 设置所属关注用户。
	 * @param accountUser
	 * 关注用户。
	 */
	public void setAccountUser(AccountUser accountUser) {
		this.accountUser = accountUser;
	}
	/**
	 * 获取消息内容。
	 * @return 消息内容。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置消息内容。
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取消息时间。
	 * @return 消息时间。
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置消息时间。
	 * @param createTime
	 * 消息时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}