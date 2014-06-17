package com.examw.wechat.message.menu;

import java.io.Serializable;
/**
 * 菜单按钮的基类。
 * @author yangyong.
 * @since 2014-02-24.
 * http://blog.csdn.net/lyq8479/article/details/9841371
 * */
public class Button implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	/**
	 * 获取按钮名称。
	 * @return 按钮名称。
	 * */
	public String getName() {
		return name;
	}
	/**
	 * 设置按钮名称。
	 * @param name
	 * 	按钮名称。
	 * */
	public void setName(String name) {
		this.name = name;
	}
}