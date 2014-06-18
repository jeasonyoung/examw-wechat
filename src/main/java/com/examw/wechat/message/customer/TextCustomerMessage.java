package com.examw.wechat.message.customer;
/**
 * 发送文本客服消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class TextCustomerMessage extends BaseCustomerMessage {
	private static final long serialVersionUID = 1L;
	private String content;
	/**
	 * 获取文本消息内容。
	 * @return 文本消息内容。
	 * */
	public String getContent() {
		return content;
	}
	/**
	 * 设置文本消息内容。
	 * @param content
	 * 	文本消息内容。
	 * */
	public void setContent(String content) {
		this.content = content;
	}
}