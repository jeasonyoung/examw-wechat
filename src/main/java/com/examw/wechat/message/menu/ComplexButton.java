package com.examw.wechat.message.menu;
/**
 * 复杂按钮(父按钮)。
 * @author yangyong.
 * @since 2014-02-25.
 * */
public class ComplexButton extends Button {
	private static final long serialVersionUID = 1L;
	private Button[] sub_button;
	/**
	 * 获取子菜单集合。
	 * @return 子菜单集合。
	 * */
	public Button[] getSub_button() {
		return sub_button;
	}
	/**
	 * 设置子菜单集合。
	 * @param sub_button
	 * 	子菜单集合。
	 * */
	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
}