package com.examw.wechat.message.req;
/**
 * 文本消息。
 * @author yangyong.
 * @since 2014-02-20.
 * */
public class TextReqMessage extends BaseReqMessage {
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