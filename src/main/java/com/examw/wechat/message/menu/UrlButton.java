package com.examw.wechat.message.menu;
/**
 * Url按钮。
 * @author yangyong.
 * @since 2014-02-27.
 * */
public class UrlButton extends Button {
	private static final long serialVersionUID = 1L;
	private String type,url;
	/**
	 * 构造函数。
	 * */
	public UrlButton(){
		this.setType("view");
	}
	/**
	 * 获取按钮类型。
	 * @return 按钮类型。
	 * */
	public String getType() {
		return type;
	}
	/**
	 * 设置按钮类型。
	 * @param type
	 * 	按钮类型。
	 * */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取Url。
	 * @return url。
	 * */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置Url
	 * @param url
	 * */
	public void setUrl(String url) {
		this.url = url;
	}
}