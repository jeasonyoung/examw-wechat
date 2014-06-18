package com.examw.wechat.message.resp;

import com.examw.wechat.message.BaseMessage;
/**
 * 响应回复文本消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class TextRespMessage extends BaseRespMessage {
	private static final long serialVersionUID = 1L;
	private String Content;
	/**
	 * 构造函数。
	 * */
	public TextRespMessage(){
		super();
		this.setMsgType("text");
	}
	/**
	 * 构造函数。
	 * @param req
	 * 	请求消息。
	 * */
	public TextRespMessage(BaseMessage req){
		super(req);
		this.setMsgType("text");
	}
	/**
	 * 获取消息内容。
	 * @return 消息内容。
	 * */
	public String getContent() {
		return Content;
	}
	/**
	 * 设置消息内容。
	 * @param content
	 * 	消息内容。
	 * */
	public void setContent(String content) {
		this.Content = content;
	}
}