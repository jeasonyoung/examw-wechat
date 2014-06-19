package com.examw.wechat.service.server;

import com.examw.wechat.message.Context;
import com.examw.wechat.message.events.EventMessage;
import com.examw.wechat.message.req.BaseReqMessage;
import com.examw.wechat.message.resp.BaseRespMessage;

/**
 * 微信消息解析接口。
 * @author yangyong.
 * @since 2014-04-10.
 * */
public interface IMessageParse {
	/**
	 * 处理普通消息请求。
	 * @param context
	 * 上下文。
	 * @param req
	 * 普通消息请求。
	 * */
	BaseRespMessage messageHandler(Context context, BaseReqMessage req);
	/**
	 * 处理事件消息请求。
	 * @param context
	 * 上下文。
	 * @param event
	 * */
	BaseRespMessage messageHandler(Context context, EventMessage event);
}