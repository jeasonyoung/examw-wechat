package com.examw.wechat.model.mgr;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;
import com.examw.wechat.support.CustomDateSerializer;

/**
 * 消息记录信息。
 * @author yangyong.
 * @since 2014-07-02.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RecordInfo extends Paging  {
	private static final long serialVersionUID = 1L;
	private String id,content,accountId, accountName,userName,openId;
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
	 * 获取所属公众号ID。
	 * @return 所属公众号ID。
	 */
	public String getAccountId() {
		return accountId;
	}
	/**
	 * 设置所属公众号ID。
	 * @param accountId
	 * 所属公众号ID。
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	/**
	 * 获取所属公众号名称。
	 * @return 所属公众号名称。
	 */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * 设置所属公众号名称。
	 * @param accountName
	 * 所属公众号名称。
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 * 获取关注用户名称。
	 * @return 关注用户名称。
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置关注用户名称。
	 * @param userName
	 * 关注用户名称。
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取关注用户OpenId.
	 * @return  关注用户OpenId.
	 */
	public String getOpenId() {
		return openId;
	}
	/**
	 * 设置关注用户OpenId.
	 * @param openId
	 * 关注用户OpenId.
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
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
	@JsonSerialize(using = CustomDateSerializer.class)
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