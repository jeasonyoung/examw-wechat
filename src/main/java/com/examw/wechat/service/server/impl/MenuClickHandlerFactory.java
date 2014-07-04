package com.examw.wechat.service.server.impl;
 
import org.apache.log4j.Logger;

import com.examw.wechat.message.BaseMessage;
import com.examw.wechat.message.Context;
import com.examw.wechat.message.events.ClickEventMessage;
import com.examw.wechat.message.resp.BaseRespMessage;
import com.examw.wechat.service.server.IMessageHandler;

/**
 *  菜单点击事件处理工厂类。
 * @author yangyong.
 * @since 2014-07-03.
 */
public class MenuClickHandlerFactory extends BaseHandlerFactory {
	private static final Logger logger = Logger.getLogger(MenuClickHandlerFactory.class);
	/*
	 * 消息处理。
	 * @see com.examw.wechat.service.server.impl.BaseHandlerFactory#handler(com.examw.wechat.message.Context, java.lang.String, com.examw.wechat.message.BaseMessage)
	 */
	@Override
	public BaseRespMessage handler(Context context, String type, BaseMessage req){
		if(logger.isDebugEnabled()) logger.debug("菜单点击消息处理工厂处理消息...");
		if(!this.preHandler(context, type, req)) return null;
		ClickEventMessage click = (ClickEventMessage)req;
		if(click == null){
			if(logger.isDebugEnabled()) logger.debug("［消息类型："+type +"］不属于菜单点击事件，无法解析请求消息为ClickEventMessage");
			return null;
		}
		 IMessageHandler handler =  this.loadHandler(click.getEventKey());
		 if(handler == null){
			 if(logger.isDebugEnabled()) logger.debug("未找到消息类型［type："+ type+" ，的菜单Key："+ click.getEventKey() +"］的处理器！");
			 return null;
		 }
		return this.run(handler,context, click.getEventKey(), click);
	}
	/*
	 * 执行处理器。
	 * @see com.examw.wechat.service.server.impl.BaseHandlerFactory#run(com.examw.wechat.service.server.IMessageHandler, com.examw.wechat.message.Context, java.lang.String, com.examw.wechat.message.BaseMessage)
	 */
	@Override
	public BaseRespMessage run(IMessageHandler handler, Context context, String type, BaseMessage req) {
		if(logger.isDebugEnabled()) logger.debug("准备开始执行处理器...");
		return handler(context, type, req);
	}
}