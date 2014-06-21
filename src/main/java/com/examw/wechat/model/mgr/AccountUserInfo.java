package com.examw.wechat.model.mgr;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;
import com.examw.wechat.support.CustomDateSerializer;
/**
 * 微信关注用户信息。
 * @author yangyong.
 * @since 2014-04-08.
 * */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AccountUserInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,openId,accountId,accountName,registerId,registerName;
	private Date createTime,lastTime;
	private Integer status; 
	/**
	 * 构造函数。
	 * */
	public AccountUserInfo(){
		this.setCreateTime(new Date());
	}
	/**
	 * 构造函数。
	 * @param accountId
	 * 微信公众号ID。
	 * @param openId
	 * 微信关注用户ID。
	 * */
	public AccountUserInfo(String accountId,String openId){
		this();
		this.setAccountId(accountId);
		this.setOpenId(openId);
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
	 * 获取所属公众号ID。
	 * @return 所属公众号ID。
	 * */
	public String getAccountId() {
		return accountId;
	}
	/**
	 * 设置所属公众号ID。
	 * @param accountId
	 * 所属公众号ID。
	 * */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	/**
	 * 获取所属公众号名称。
	 * @return 所属公众号名称。
	 * */
	public String getAccountName() {
		return accountName;
	}
	/**
	 * 设置所属公众号名称。
	 * @param accountName
	 * 所属公众号名称。
	 * */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	/**
	 * 获取注册用户ID。
	 * @return 注册用户ID。
	 */
	public String getRegisterId() {
		return registerId;
	}
	/**
	 * 设置注册用户ID。
	 * @param registerId
	 * 注册用户ID。
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}
	/**
	 * 获取注册用户名称。
	 * @return 注册用户名称。
	 */
	public String getRegisterName() {
		return registerName;
	}
	/**
	 * 设置注册用户名称。
	 * @param registerName
	 * 注册用户名称。
	 */
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}
	/**
	 * 获取首次关注时间。
	 * @return 首次关注时间。
	 * */
	@JsonSerialize(using = CustomDateSerializer.class)
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
	@JsonSerialize(using = CustomDateSerializer.class)
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
}