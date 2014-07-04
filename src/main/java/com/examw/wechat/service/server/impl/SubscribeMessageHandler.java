package com.examw.wechat.service.server.impl;

import org.apache.log4j.Logger;

import com.examw.wechat.message.BaseMessage;
import com.examw.wechat.message.Context;
import com.examw.wechat.message.resp.BaseRespMessage;
import com.examw.wechat.message.resp.TextRespMessage;
import com.examw.wechat.service.mgr.IAccountUserService;
import com.examw.wechat.service.server.IMessageHandler;

/**
 * 微信关注消息处理。
 * @author yangyong.
 * @since 2014-07-02.
 */
public class SubscribeMessageHandler implements IMessageHandler {
	private static final Logger logger = Logger.getLogger(SubscribeMessageHandler.class);
	private IAccountUserService accountUserService;
	private String welcome;
	/**
	 * 设置关注用户服务接口。
	 * @param accountUserService
	 */
	public void setAccountUserService(IAccountUserService accountUserService) {
		this.accountUserService = accountUserService;
	}
	/**
	 * 设置欢迎信息。
	 * @param welcome
	 */
	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}
	/*
	 * 关注消息处理。
	 * @see com.examw.wechat.service.server.IMessageHandler#handler(com.examw.wechat.message.Context, java.lang.String, com.examw.wechat.message.BaseMessage)
	 */
	@Override
	public BaseRespMessage handler(Context context, String type, BaseMessage req) {
		if(logger.isDebugEnabled())logger.debug("开始关注事件处理...");
		this.accountUserService.addSubscribe(context);
		TextRespMessage respMessage = new TextRespMessage(req);
		respMessage.setContent(this.welcome);
		if(logger.isDebugEnabled()){
			logger.debug(respMessage.getContent());
			logger.debug("关注事件处理完毕！");
		}
		return respMessage;
	}
}