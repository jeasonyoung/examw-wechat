package com.examw.wechat.message.events;
/**
 * 自定义菜单事件消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class ClickEventMessage extends EventMessage {
	private static final long serialVersionUID = 1L;
	private String eventKey;
	/**
	 * 获取事件KEY值，与自定义菜单接口中KEY值对应。
	 * @return 事件KEY值。
	 * */
	public String getEventKey() {
		return eventKey;
	}
	/**
	 * 设置事件KEY值，与自定义菜单接口中KEY值对应。
	 * @param eventKey
	 * 	事件KEY值。
	 * */
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
}