package com.examw.wechat.message;

import java.io.Serializable;
/**
 * 微信通用接口凭证。
 * @author yangyong。
 * @since 2014-02-20.
 * */
public class Token implements Serializable {
	private static final long serialVersionUID = 1L;
	private String token;
	private Integer expiresIn;
	/**
	 * 获取凭证。
	 * @return 凭证。
	 * */
	public String getToken() {
		return token;
	}
	/**
	 * 设置凭证。
	 * @param token
	 * 	凭证。
	 * */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * 获取凭证有效时间(单位：秒)。
	 * @return 凭证有效时间(单位：秒)。
	 * */
	public Integer getExpiresIn() {
		return expiresIn;
	}
	/**
	 * 设置凭证有效时间(单位：秒)。
	 * @param expiresIn
	 * 	凭证有效时间(单位：秒)。
	 * */
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}
}