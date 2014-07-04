package com.examw.wechat.service.server.impl;

import org.apache.log4j.Logger;

import com.examw.wechat.message.BaseMessage;
import com.examw.wechat.message.Context;
import com.examw.wechat.message.events.ClickEventMessage;
import com.examw.wechat.message.req.TextReqMessage;
import com.examw.wechat.message.resp.BaseRespMessage;
import com.examw.wechat.message.resp.TextRespMessage;
import com.examw.wechat.service.server.IMessageHandler;
/**
 * 默认消息处理器。
 * @author yangyong.
 * @since 2014-07-02.
 */
public class DefaultMessageHandler implements IMessageHandler {
	private static Logger logger = Logger.getLogger(DefaultMessageHandler.class);
	private boolean authen = true;
	private String authenMenuKey = "authen", authenMsg,clickMsg,textMsg;
	/**
	 * 设置是否用户认证。
	 * @param authen
	 * 是否用户认证（默认：须认证）。
	 */
	public void setAuthen(boolean authen) {
		this.authen = authen;
	}
	/**
	 * 设置认证菜单键值。
	 * @param authenMenuKey
	 */
	public void setAuthenMenuKey(String authenMenuKey) {
		this.authenMenuKey = authenMenuKey;
	}
	/**
	 * 设置认证提示信息。
	 * @param authenMsg
	 */
	public void setAuthenMsg(String authenMsg){
		this.authenMsg = authenMsg;
	}
	/**
	 *  设置菜单点击事件提示信息。
	 * @param clickMsg
	 */
	public void setClickMsg(String clickMsg) {
		this.clickMsg = clickMsg;
	}
	/**
	 * 设置文本消息提示信息。
	 * @param textMsg
	 */
	public void setTextMsg(String textMsg) {
		this.textMsg = textMsg;
	}
	/*
	 * 事件处理。
	 * @see com.examw.wechat.service.server.IMessageHandler#handler(com.examw.wechat.message.Context, java.lang.String, com.examw.wechat.message.BaseMessage)
	 */
	@Override
	public BaseRespMessage handler(Context context, String type, BaseMessage req) {
		if(logger.isDebugEnabled()) logger.debug("开始默认菜单点击处理...");
		if(this.authen && !context.isAuthen()){
			TextRespMessage resp = new TextRespMessage(req);
			context.setLastMenuKey(this.authenMenuKey);
			resp.setContent(authenMsg);
			if(logger.isDebugEnabled()) logger.debug(resp.getContent());
			return resp;
		}
		if(logger.isDebugEnabled()) logger.debug("当前请求用户ID：" + context.getUserId());
		return this.run(context, type, req);
	}
	/**
	 * 执行处理。
	 * @param context
	 * 上下文。
	 * @param menuKey
	 * 菜单代码。
	 * @param req
	 * 请求消息。
	 * @return
	 * 反馈信息。
	 */
	protected BaseRespMessage run(Context context, String menuKey, BaseMessage req){
		if(logger.isDebugEnabled()) logger.debug("开始执行处理...");
		//请求消息为菜单点击事件。
		if(req instanceof ClickEventMessage){
			if(logger.isDebugEnabled()) logger.debug("当前请求消息类型［"+req.getMsgType()+"］为菜单点击事件.");
			return this.runClickReq(context, menuKey, (ClickEventMessage)req);
		}
		//请求消息为文本消息
		if(req instanceof TextReqMessage){
			if(logger.isDebugEnabled()) logger.debug("当前请求消息类型［"+req.getMsgType()+"］为文本消息.");
			return this.runTextReq(context, menuKey, (TextReqMessage)req);
		}
		if(logger.isDebugEnabled()) logger.debug("当前请求消息类型［"+req.getMsgType()+"］，程序未实现其处理函数！");
		return new TextRespMessage(req);
	}
	/**
	 * 由点击事件触发的消息处理。
	 * @param context
	 * 上下文。
	 * @param menuKey
	 * 菜单代码。
	 * @param click
	 * 点击事件消息。
	 * @return
	 */
	protected BaseRespMessage runClickReq(Context context, String menuKey,ClickEventMessage req){
		TextRespMessage resp = new TextRespMessage(req);
		resp.setContent(String.format(this.clickMsg, menuKey));
		if(logger.isDebugEnabled()) {
			logger.debug("[menuKey="+menuKey+"][event="+req.getEvent()+",eventKey="+req.getEventKey()+"]");
			logger.debug(resp.getContent());
		}
		return resp;
	}
	/**
	 * 文本消息处理。
	 * @param context
	 * @param menuKey
	 * @param req
	 * @return
	 */
	protected BaseRespMessage runTextReq(Context context, String menuKey,TextReqMessage req){
		TextRespMessage resp = new TextRespMessage(req);
		resp.setContent(this.textMsg);
		if(logger.isDebugEnabled()) {
			logger.debug("[menuKey="+menuKey+"]");
			logger.debug("内容：" + req.getContent());
			logger.debug(resp.getContent());
		}
		return resp;
	}
}