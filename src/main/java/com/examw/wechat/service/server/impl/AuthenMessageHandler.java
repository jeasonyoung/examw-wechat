package com.examw.wechat.service.server.impl;

import org.apache.log4j.Logger;

import com.examw.wechat.message.BaseMessage;
import com.examw.wechat.message.Context;
import com.examw.wechat.message.events.ClickEventMessage;
import com.examw.wechat.message.req.TextReqMessage;
import com.examw.wechat.message.resp.BaseRespMessage;
import com.examw.wechat.message.resp.TextRespMessage;

/**
 * 认证消息处理。
 * @author yangyong.
 * @since 2014-07-03.
 */
public class AuthenMessageHandler extends DefaultMessageHandler {
	 private static final Logger logger = Logger.getLogger(AuthenMessageHandler.class);
	 /*
	  * 消息处理。
	  * @see com.examw.wechat.service.server.impl.DefaultClickEventHandler#handler(com.examw.wechat.message.Context, java.lang.String, com.examw.wechat.message.BaseMessage)
	  */
	 @Override
	 public BaseRespMessage handler(Context context, String type, BaseMessage req){
		 if(logger.isDebugEnabled()) logger.debug("开始认证消息处理...");
		 this.setAuthen(false);
		return super.handler(context, type, req);
	 }
	 /*
	  * 由点击触发的认证消息。
	  * @see com.examw.wechat.service.server.impl.DefaultClickEventHandler#runClickReq(com.examw.wechat.message.Context, java.lang.String, com.examw.wechat.message.events.ClickEventMessage)
	  */
	 @Override
	 protected BaseRespMessage runClickReq(Context context, String menuKey,ClickEventMessage req){
		 if(logger.isDebugEnabled()) logger.debug("收到认证菜单［menuKey:"+menuKey+"］点击事件消息..");
		 TextRespMessage resp = new TextRespMessage(req);
		 resp.setContent((context.isAuthen() ? "您已是认证用户！如须更换绑定的注册信息，" : "" ) + " 请输入您注册时填写的手机号码：");
		 if(logger.isDebugEnabled()) logger.debug(resp.getContent());
		 return resp;
	 }
	 /*
	  *  文本消息处理。
	  * @see com.examw.wechat.service.server.impl.DefaultMessageHandler#runTextReq(com.examw.wechat.message.Context, java.lang.String, com.examw.wechat.message.req.TextReqMessage)
	  */
	 protected BaseRespMessage runTextReq(Context context, String menuKey,TextReqMessage req){
		 return null;
	 }
}