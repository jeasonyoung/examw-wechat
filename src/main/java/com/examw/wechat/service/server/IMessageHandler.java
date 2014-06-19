package com.examw.wechat.service.server;

import com.examw.wechat.message.BaseMessage;
import com.examw.wechat.message.Context;
import com.examw.wechat.message.resp.BaseRespMessage;

/**
 * 消息处理接口。
 * @author yangyong.
 * @since 2014-06-18.
 */
public interface IMessageHandler {
	/**
	 * 消息处理。
	 * @param context
	 * 上下文。
	 * @param type
	 * 消息类型。
	 * @param req
	 * 消息对象。
	 * @return
	 * 反馈消息对象。
	 */
	BaseRespMessage handler(Context context, String type, BaseMessage req);
}