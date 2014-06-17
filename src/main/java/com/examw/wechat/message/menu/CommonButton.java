package com.examw.wechat.message.menu;
/**
 * 普通按钮(子按钮)。
 * @author yangyong.
 * @since 2014-02-25.
 * */
public class CommonButton extends Button {
	private static final long serialVersionUID = 1L;
	private String type,key;
	/**
	 * 构造函数。
	 * */
	public CommonButton(){
		this.type = "click";
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
	 * 获取按钮键值。
	 * @return 按钮键值。
	 * */
	public String getKey() {
		return key;
	}
	/**
	 * 设置按钮键值。
	 * @param key
	 * 	按钮键值。
	 * */
	public void setKey(String key) {
		this.key = key;
	}
}