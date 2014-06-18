package com.examw.wechat.message.customer;

import com.examw.wechat.message.Image;

/**
 * 发送图片客服消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class ImageCustomerMessage extends BaseCustomerMessage {
	private static final long serialVersionUID = 1L;
	private Image image;
	/**
	 * 获取图片信息。
	 * @return 图片信息。
	 * */
	public Image getImage() {
		return image;
	}
	/**
	 * 设置图片信息。
	 * @param image
	 * 	图片信息。
	 * */
	public void setImage(Image image) {
		this.image = image;
	}
}