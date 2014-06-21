package com.examw.wechat.domain.mgr;

import java.io.Serializable;
import java.util.Date;

import com.examw.wechat.domain.account.Account;
/**
 * 微信用户信息。
 * @author yangyong.
 * 
 * */
public class AccountUser implements Serializable {
	private static final long serialVersionUID = 1L;
	public final static int USER_STATUS_SUBSCRIBE = 1, USER_STATUS_UNSUBSCRIBE = -1;
	private String id,openId;
	private Account account;
	private Register register;
	private Integer status;
	private Date createTime,lastTime;
	/**
	 * 构造函数。
	 * */
	public AccountUser(){
		this.lastTime = this.createTime = new Date();
		this.status = AccountUser.USER_STATUS_SUBSCRIBE;
	}
	/**
	 * 获取关注ID。
	 * @return 关注ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置关注ID。
	 * @param id
	 * 关注ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取微信ID。
	 * @return 微信ID。
	 * */
	public String getOpenId() {
		return openId;
	}
	/**
	 * 设置微信ID。
	 * @param openId
	 * 微信ID。
	 * */
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	/**
	 * 获取所关注的微信公众号。
	 * @return 所关注的微信公众号。
	 * */
	public Account getAccount() {
		return account;
	}
	/**
	 * 设置所关注的微信公众号。
	 * @param account
	 *	所关注的微信公众号。
	 * */
	public void setAccount(Account account) {
		this.account = account;
	}
	/**
	 * 获取所属登记信息。
	 * @return 所属登记信息。
	 */
	public Register getRegister() {
		return register;
	}
	/**
	 * 设置所属登记信息。
	 * @param register
	 * 所属登记信息。
	 */
	public void setRegister(Register register) {
		this.register = register;
	}
	/**
	 * 获取用户状态(1-关注，－1-取消关注)。
	 * @return 用户状态(1-关注，－1-取消关注)。
	 * */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置用户状态(1-关注，－1-取消关注)。
	 * @param status
	 * 用户状态(1-关注，－1-取消关注)。
	 * */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取首次关注时间。
	 * @return 首次关注时间。
	 * */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置首次关注时间。
	 * @param createTime
	 * 首次关注时间。
	 * */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取最近一次交互时间。
	 * @return 最近一次交互时间。
	 * */
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置最近一次交互时间。
	 * @param lastTime
	 * 	最近一次交互时间。
	 * */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
}