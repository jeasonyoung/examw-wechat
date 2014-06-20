package com.examw.wechat.service.server.impl;

import com.examw.wechat.message.BaseMessage;
import com.examw.wechat.message.Context;
import com.examw.wechat.message.resp.BaseRespMessage;
import com.examw.wechat.service.server.IMessageHandler;

/**
 * 消息处理实现。
 * @author yangyong.
 * @since 2014-06-20.
 */
public class MessageHandlerImpl implements IMessageHandler {
	/*
	 * 消息处理。
	 * @see com.examw.wechat.service.server.IMessageHandler#handler(com.examw.wechat.message.Context, java.lang.String, com.examw.wechat.message.BaseMessage)
	 */
	@Override
	public BaseRespMessage handler(Context context, String type, BaseMessage req) {
		// TODO Auto-generated method stub
		return null;
	}
}