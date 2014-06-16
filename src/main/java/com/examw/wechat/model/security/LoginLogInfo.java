package com.examw.wechat.model.security;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;
import com.examw.wechat.support.CustomDateSerializer;
/**
 * 登录日志信息。
 * @author yangyong.
 * @since 2014-05-17.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class LoginLogInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,account,ip,browser;
	private Date time;
	/**
	 * 获取日志ID。
	 * @return
	 * 日志ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置日志ID。
	 * @param id
	 * 日志ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取登录账号。
	 * @return
	 * 登录账号。
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置登录账号。
	 * @param account
	 * 登录账号。
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取登录IP。
	 * @return
	 * 登录IP。
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * 设置登录IP。
	 * @param ip
	 * 登录IP。
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * 获取浏览器版本。
	 * @return
	 * 浏览器版本。
	 */
	public String getBrowser() {
		return browser;
	}
	/**
	 * 设置浏览器版本。
	 * @param browser
	 * 浏览器版本。
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	/**
	 * 获取登录时间。
	 * @return
	 * 登录时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getTime() {
		return time;
	}
	/**
	 * 设置登录时间。
	 * @param time
	 * 登录时间。
	 */
	public void setTime(Date time) {
		this.time = time;
	}
}