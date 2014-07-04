package com.examw.wechat.service.server.impl;

import org.apache.log4j.Logger;

import com.examw.wechat.dao.mgr.IRegisterDao;
import com.examw.wechat.domain.mgr.AccountUser;
import com.examw.wechat.domain.mgr.Register;
import com.examw.wechat.message.BaseMessage;
import com.examw.wechat.message.Context;
import com.examw.wechat.message.events.ClickEventMessage;
import com.examw.wechat.message.req.TextReqMessage;
import com.examw.wechat.message.resp.BaseRespMessage;
import com.examw.wechat.message.resp.TextRespMessage;
import com.examw.wechat.service.mgr.IAccountUserService;

/**
 * 认证消息处理。
 * @author yangyong.
 * @since 2014-07-03.
 */
public class AuthenMessageHandler extends DefaultMessageHandler {
	 private static final Logger logger = Logger.getLogger(AuthenMessageHandler.class);
	 private IAccountUserService accountUserService;
	 private IRegisterDao registerDao;
	 private String isAuthenMsg, authenSuccessMsg, msg, noFindRegisterMsg;
	 /**
	  * 设置已认证用户提示信息。
	  * @param isAuthenMsg
	  */
	public void setIsAuthenMsg(String isAuthenMsg) {
		this.isAuthenMsg = isAuthenMsg;
	}
	/**
	 * 设置认证成功提示信息。
	 * @param authenSuccessMsg
	 */
	public void setAuthenSuccessMsg(String authenSuccessMsg) {
		this.authenSuccessMsg = authenSuccessMsg;
	}
	/**
	 * 设置未认证用户提示信息。
	 * @param msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * 设置未找到登记提示信息。
	 * @param noFindRegisterMsg
	 */
	public void setNoFindRegisterMsg(String noFindRegisterMsg) {
		this.noFindRegisterMsg = noFindRegisterMsg;
	}
	/**
	 * 设置关注用户信息。
	 * @param accountUserService
	 */
	public void setAccountUserService(IAccountUserService accountUserService) {
		this.accountUserService = accountUserService;
	}
	/**
	 * 设置注册数据访问接口。
	 * @param registerDao
	 */
	public void setRegisterDao(IRegisterDao registerDao) {
		this.registerDao = registerDao;
	}
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
		 resp.setContent((context.isAuthen() ? this.isAuthenMsg : "" ) + this.msg);
		 if(logger.isDebugEnabled()) logger.debug(resp.getContent());
		 return resp;
	 }
	 /*
	  *  文本消息处理。
	  * @see com.examw.wechat.service.server.impl.DefaultMessageHandler#runTextReq(com.examw.wechat.message.Context, java.lang.String, com.examw.wechat.message.req.TextReqMessage)
	  */
	 protected BaseRespMessage runTextReq(Context context, String menuKey,TextReqMessage req){
		 if(logger.isDebugEnabled()) logger.debug("开始认证信息处理..");
		 TextRespMessage resp = new TextRespMessage(req);
		  this.addAuthen(resp,context, req.getContent());
		  if(logger.isDebugEnabled()){
			  logger.debug("回复:" + resp.getContent());
			  logger.debug("认证信息处理完毕！");
		  }
		  return resp;
	 }
	 //添加认证
	 private void addAuthen(TextRespMessage resp, Context context, String mobile){
		 Register register = this.registerDao.loadRegister(mobile);
		 if(register == null){
			 if(logger.isDebugEnabled()) logger.debug("未找到［手机号码："+mobile+"］的登记信息！");
			 resp.setContent(String.format(this.noFindRegisterMsg, mobile));
			 return;
		 }
		 AccountUser accountUser = this.accountUserService.addSubscribe(context);
		 if(accountUser == null){
			 String error = "获取关注用户信息时失败！";
			logger.error(error);
			 throw new RuntimeException(error);
		 }
		 //写入关注用户信息
		 accountUser.setRegister(register);
		 //写入上下文
		 context.setUserId(register.getId());
		 //反馈信息。
		 resp.setContent(String.format(this.authenSuccessMsg, 
				 						register.getName(),
				 						register.getMobile(), 
				 						((register.getProvince() == null) ? "" : register.getProvince().getName()),
				 						((register.getExam() == null) ? "" : register.getExam().getName())));
	 }
}