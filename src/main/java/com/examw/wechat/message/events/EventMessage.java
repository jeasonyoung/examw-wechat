package com.examw.wechat.message.events;

import com.examw.wechat.message.BaseMessage;

/**
 * 事件消息类。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class EventMessage extends BaseMessage {
	private static final long serialVersionUID = 1L;
	private String event;
	/**
	 * 获取事件类型。
	 * @return 事件类型。
	 * */
	public String getEvent() {
		return event;
	}
	/**
	 * 设置事件类型。
	 * @param event
	 * 	事件类型。
	 * */
	public void setEvent(String event) {
		this.event = event;
	}
}