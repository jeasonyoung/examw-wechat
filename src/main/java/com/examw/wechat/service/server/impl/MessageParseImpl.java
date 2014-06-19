package com.examw.wechat.service.server.impl;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.wechat.message.Context;
import com.examw.wechat.message.events.ClickEventMessage;
import com.examw.wechat.message.events.EventMessage;
import com.examw.wechat.message.events.LocationEventMessage;
import com.examw.wechat.message.events.ScanEventMessage;
import com.examw.wechat.message.events.SubscribeEventMessage;
import com.examw.wechat.message.events.UnsubscribeEventMessage;
import com.examw.wechat.message.events.ViewEventMessage;
import com.examw.wechat.message.req.BaseReqMessage;
import com.examw.wechat.message.req.ImageReqMessage;
import com.examw.wechat.message.req.LinkReqMessage;
import com.examw.wechat.message.req.LocationReqMessage;
import com.examw.wechat.message.req.TextReqMessage;
import com.examw.wechat.message.req.VideoReqMessage;
import com.examw.wechat.message.req.VoiceReqMessage;
import com.examw.wechat.message.resp.BaseRespMessage;
import com.examw.wechat.service.server.IMessageHandler;
import com.examw.wechat.service.server.IMessageParse;
/**
 * 微信消息解析实现类。
 * @author yangyong.
 * @since 2014-04-12.
 * */
public class MessageParseImpl implements IMessageParse {
	private static Logger logger = Logger.getLogger(MessageParseImpl.class);
	private IMessageHandler handler;
	/**
	 * 设置消息处理接口。
	 * @param handler
	 */
	public void setHandler(IMessageHandler handler) {
		this.handler = handler;
	}
	/*
	 * 消息处理。
	 * @see com.examw.wechat.service.server.IMessageHandlers#messageHandler(com.examw.wechat.message.Context, com.examw.wechat.message.req.BaseReqMessage)
	 */
	@Override
	public BaseRespMessage messageHandler(Context context, BaseReqMessage req) {
		logger.info("开始普通消息处理...");
		switch(req.getMsgType()){
			//文本消息。
			case Context.REQ_MESSAGE_TYPE_TEXT:{
				logger.info("接收到文本消息");
				//菜单处理。
				if(!StringUtils.isEmpty(context.getLastMenuKey())){
					logger.info("获取到上下文菜单：" + context.getLastMenuKey() + ",交由该菜单处置...");
				}
				return this.handler.handler(context, req.getMsgType(), (TextReqMessage)req);
			}
			//图片消息。
			case  Context.REQ_MESSAGE_TYPE_IMAGE:{
				logger.info("接收到图片消息");
				return this.handler.handler(context, req.getMsgType(), (ImageReqMessage)req);
			}
			//链接消息。
			case Context.REQ_MESSAGE_TYPE_LINK:{
				logger.info("接收到链接消息");
				return this.handler.handler(context, req.getMsgType(), (LinkReqMessage)req);
			}
			//地理位置消息。
			case Context.REQ_MESSAGE_TYPE_LOCATION:{
				logger.info("接收到地理位置消息");
				return this.handler.handler(context, req.getMsgType(), (LocationReqMessage)req);
			}
			//视频消息。
			case Context.REQ_MESSAGE_TYPE_VIDEO:{
				logger.info("接收到视频消息");
				return this.handler.handler(context, req.getMsgType(), (VideoReqMessage)req);
			}
			//语音消息。
			case Context.REQ_MESSAGE_TYPE_VOICE:{
				logger.info("接收到视频消息");
				return this.handler.handler(context, req.getMsgType(), (VoiceReqMessage)req);
			}
			default:{
				logger.info("消息类型["+req.getMsgType()+"]不可知，不能处理！");
				return null;
			}
		}
	}
	/*
	 * 事件处理。
	 * @see com.examw.wechat.service.server.IMessageHandlers#messageHandler(com.examw.wechat.message.Context, com.examw.wechat.message.events.EventMessage)
	 */
	@Override
	public BaseRespMessage messageHandler(Context context, EventMessage event) {
		logger.info("开始事件消息处理...");
		switch(event.getEvent()){
			//关注事件。
			case Context.EVENT_MESSAGE_TYPE_SUBSCRIBE:{
				logger.info("接收到关注事件,交付默认事件消息处理...");
				return this.handler.handler(context, event.getEvent(), (SubscribeEventMessage)event);
			}
			//扫描事件。
			case Context.EVENT_MESSAGE_TYPE_SCAN:{
				logger.info("接收到扫描事件");
				return this.handler.handler(context, event.getEvent(), (ScanEventMessage)event);
			}
			//取消关注事件。
			case Context.EVENT_MESSAGE_TYPE_UNSUBSCRIBE:{
				logger.info("接收到取消关注事件,交付默认事件消息处理...");
				return this.handler.handler(context, event.getEvent(), (UnsubscribeEventMessage)event);
			}
			//上报地理位置事件。
			case Context.EVENT_MESSAGE_TYPE_LOCATION:{
				logger.info("接收到上报地理位置事件");
				return this.handler.handler(context, event.getEvent(), (LocationEventMessage)event);
			}
			//菜单点击事件。
			case Context.EVENT_MESSAGE_TYPE_CLICK:{
				logger.info("接收到菜单点击事件");
				ClickEventMessage click = (ClickEventMessage)event;
				//菜单处理。
				if(!context.getLastMenuKey().equalsIgnoreCase(click.getEventKey())){
					 logger.info("菜单值:" + click.getEventKey() + ",与上下文菜单值:"  + context.getLastMenuKey() + ",不一致更新上下文中的菜单值。");
					 context.setLastMenuKey(click.getEventKey());
				}
				return this.handler.handler(context, event.getEvent(), (ClickEventMessage)event);
			}
			//菜单跳转事件
			case Context.EVENT_MESSAGE_TYPE_VIEW:{
				logger.info("接收到菜单跳转事件事件");
				return this.handler.handler(context, event.getEvent(), (ViewEventMessage)event);
			}
			default:{
				logger.info("事件消息类型["+ event.getEvent() +"]不可知，不能处理！");
				return null;
			}
		}
	}
}