package com.examw.wechat.service.server.impl;

import org.apache.log4j.Logger;

import com.examw.wechat.message.BaseMessage;
import com.examw.wechat.message.Context;
import com.examw.wechat.message.req.TextReqMessage;
import com.examw.wechat.message.resp.BaseRespMessage;
import com.examw.wechat.service.server.IMessageHandler;

/**
 * 文本消息处理工厂。
 * @author yangyong.
 * @since 2014-07-03.
 */
public class TextMessageHandlerFactory extends BaseHandlerFactory {
	private static final Logger logger = Logger.getLogger(TextMessageHandlerFactory.class);
	/*
	 * 消息处理。
	 * (non-Javadoc)
	 * @see com.examw.wechat.service.server.impl.BaseHandlerFactory#handler(com.examw.wechat.message.Context, java.lang.String, com.examw.wechat.message.BaseMessage)
	 */
	@Override
	public BaseRespMessage handler(Context context, String type, BaseMessage req){
		if(logger.isDebugEnabled()) logger.debug("文本消息处理工厂处理消息...");
		if(!this.preHandler(context, type, req)) return null;
		TextReqMessage text = (TextReqMessage)req;
		if(text == null){
			if(logger.isDebugEnabled()) logger.debug("［消息类型："+type +"］不属于文本消息，无法解析请求消息为TextReqMessage");
			return null;
		}
		String key = context.getLastMenuKey();
		 IMessageHandler handler =  this.loadHandler(key);
		 if(handler == null){
			 if(logger.isDebugEnabled()) logger.debug("未找到消息类型［type："+ type+" ，的菜单Key："+ key +"］的处理器！");
			 return null;
		 }
		return this.run(handler,context,key, text);
	}
	/* 
	 * 执行处理器。
	 * @see com.examw.wechat.service.server.impl.BaseHandlerFactory#run(com.examw.wechat.service.server.IMessageHandler, com.examw.wechat.message.Context, java.lang.String, com.examw.wechat.message.BaseMessage)
	 */
	@Override
	protected BaseRespMessage run(IMessageHandler handler, Context context, String type, BaseMessage req) {
		if(logger.isDebugEnabled()) logger.debug("准备开始执行处理器...");
		return handler(context, type, req);
	}
}