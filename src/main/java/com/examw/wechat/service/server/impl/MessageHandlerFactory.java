package com.examw.wechat.service.server.impl;

import org.apache.log4j.Logger;

import com.examw.wechat.message.BaseMessage;
import com.examw.wechat.message.Context; 
import com.examw.wechat.message.resp.BaseRespMessage;  
import com.examw.wechat.service.server.IMessageHandler;
/**
 * 消息处理实现。
 * @author yangyong.
 * @since 2014-06-20.
 */
public class MessageHandlerFactory extends BaseHandlerFactory {
	private static final Logger logger = Logger.getLogger(MessageHandlerFactory.class);
	/*
	 * 执行处理器
	 * @see com.examw.wechat.service.server.impl.BaseHandlerFactory#run(com.examw.wechat.service.server.IMessageHandler, com.examw.wechat.message.Context, java.lang.String, com.examw.wechat.message.BaseMessage)
	 */
	@Override
	protected BaseRespMessage run(IMessageHandler handler, Context context, String type, BaseMessage req) {
		if(logger.isDebugEnabled()) logger.debug("准备开始执行处理器...");
		return handler(context, type, req);
	} 
}