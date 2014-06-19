package com.examw.wechat.service.server.impl;
 
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.wechat.dao.account.IAccountDao;
import com.examw.wechat.domain.account.Account;
import com.examw.wechat.message.Context;
import com.examw.wechat.message.events.ClickEventMessage;
import com.examw.wechat.message.events.EventMessageHelper;
import com.examw.wechat.message.req.BaseReqMessage;
import com.examw.wechat.message.req.ReqMessageHelper;
import com.examw.wechat.message.req.TextReqMessage;
import com.examw.wechat.message.resp.BaseRespMessage;
import com.examw.wechat.message.resp.RespMesssageHelper;
import com.examw.wechat.message.resp.TextRespMessage;
import com.examw.wechat.service.server.IContextService;
import com.examw.wechat.service.server.ICoreService;
import com.examw.wechat.service.server.IMessageParse;
import com.examw.wechat.support.SignUtil;
/**
 * 微信通信核心服务接口默认实现。
 * @author yangyong.
 * @since 2014-03-31.
 * */
public class DefaultCoreServiceImpl implements ICoreService {
	private static Logger logger = Logger.getLogger(DefaultCoreServiceImpl.class);
	private IAccountDao accountDao;
	private IContextService contextService;
	private IMessageParse messageParse;
	private String bindAccountOrderPattern;
	/**
	 *  构造函数。
	 * */
	public DefaultCoreServiceImpl(){
		this.setBindAccountOrderPattern("\\[Order:init:(.+)\\]");
	}
	/**
	 * 设置微信公众号绑定注册信息指令正则表达式。
	 * */
	public void setBindAccountOrderPattern(String bindAccountOrderPattern) {
		this.bindAccountOrderPattern = bindAccountOrderPattern;
	}
	/**
	 * 设置微信账号数据访问接口。
	 * @param weChatAccountDao
	 * 微信账号数据访问接口。
	 * */
	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}
	/**
	 * 设置微信用户上下文服务接口。
	 * @param contextService
	 * 微信用户上下文服务接口。
	 * */
	public void setContextService(IContextService contextService) {
		this.contextService = contextService;
	}
	/**
	 * 微信消息中心接口。
	 * @param messageParse
	 */
	public void setMessageParse(IMessageParse messageParse) {
		this.messageParse = messageParse;
	}
	/*
	 * 校验微信令牌.
	 * @see com.examw.wechat.service.server.ICoreService#checkSignature(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public synchronized boolean checkSignature(String signature, String timestamp, String nonce) throws Exception {
		if(StringUtils.isEmpty(signature) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce)) return false;
		logger.info("开始校验微信接入令牌...");
		String err = null;
		List<Account> accounts = this.accountDao.loadAllAccounts();
		if(accounts == null || accounts.size() == 0){
			logger.info(err = "系统中未登记任何微信公众账号信息！");
			throw new RuntimeException(err);
		}
		for(Account account: accounts){
			if(account == null || account.getStatus() == Account.ACCOUNT_STATUS_DISABLE) continue;
			if(StringUtils.isEmpty(account.getToken())) continue;
			if(SignUtil.checkSignature(signature, account.getToken(), timestamp, nonce)){
				logger.info("token:" + account.getToken());
				logger.info("校验微信接入令牌结束！");
				return true;
			}
		}
		logger.info(err = "校验微信接入令牌失败！");
		return false;
	}
	/*
	 * 处理微信请求。
	 * @see com.examw.wechat.service.server.ICoreService#processRequest(java.util.Map)
	 */
	@Override
	public synchronized String processRequest(Map<String, String> dataMap) {
		try {
			String account_openId = dataMap.get(REQ_MSG_ToUserName),
					  user_openId = dataMap.get(REQ_MSG_FromUserName),
					  msg_type = dataMap.get(REQ_MSG_MsgType);
			Account account =	this.accountDao.loadAccount(account_openId);
			if(account == null){
				if(Context.REQ_MESSAGE_TYPE_TEXT.equalsIgnoreCase(msg_type)){
					//检测绑定[Order:init:公众号]  
					String result = this.bindAccountRegister(ReqMessageHelper.parseTextReqMessage(dataMap));
					if(!StringUtils.isEmpty(result)) return result;
				}
				return this.createBackTextMessage(user_openId, account_openId, "公众号未被绑定注册，请联系客服人员！ ");
			}
			if(account.getStatus() == Account.ACCOUNT_STATUS_DISABLE){
				return this.createBackTextMessage(user_openId, account_openId, "公众号未被绑定注册，请联系客服人员！ ");
			}
			//加载上下文。
			logger.info("加载上下文...");
			Context context = this.contextService.get(account.getId(), user_openId);
			if(context == null){
				logger.error("加载上下文失败！");
				return null;
			}
			//消息处理
			BaseRespMessage resp = null;
 			switch(msg_type){
 				//事件消息。
				case Context.REQ_MESSAGE_TYPE_EVENT: {
					logger.info("解析为事件消息处理。");
					resp = this.buildMessageEventHandlers(context, dataMap);
				}
				break;
				//图片消息。
				case Context.REQ_MESSAGE_TYPE_IMAGE:{
					logger.info("解析为图片消息处理。");
					resp = this.messageParse.messageHandler(context, ReqMessageHelper.parseImageReqMessage(dataMap));
				}
				break;
				//链接消息。
				case Context.REQ_MESSAGE_TYPE_LINK:{
					logger.info("解析为链接消息处理。");
					resp = this.messageParse.messageHandler(context, ReqMessageHelper.parseLinkReqMessage(dataMap));
				}
				break;
				//地理位置消息。
				case Context.REQ_MESSAGE_TYPE_LOCATION:{
					logger.info("解析为地理位置消息处理。");
					resp = this.messageParse.messageHandler(context, ReqMessageHelper.parseLocationReqMessage(dataMap));
				}
				break;
				//文本消息。
				case Context.REQ_MESSAGE_TYPE_TEXT:{
					logger.info("解析为文本消息处理。");
					resp = this.messageParse.messageHandler(context, ReqMessageHelper.parseTextReqMessage(dataMap));
				}
				break;
				//视频消息。
				case Context.REQ_MESSAGE_TYPE_VIDEO:{
					logger.info("解析为视频消息处理。");
					resp = this.messageParse.messageHandler(context, ReqMessageHelper.parseVideoReqMessage(dataMap));
				}
				break;
				//语音消息。
				case Context.REQ_MESSAGE_TYPE_VOICE:{
					logger.info("解析为语音消息处理。");
					resp = this.messageParse.messageHandler(context, ReqMessageHelper.parseVoiceReqMessage(dataMap));
				}
				break;
			}
			if(resp != null){
				this.contextService.update(context);
				return RespMesssageHelper.respMessageToXml(resp);
			}
		} catch (Exception e) {
			logger.info("处理微信请求时发生异常:" + e.getMessage());
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 构建消息事件处理。
	 * @param context
	 * 上下文。
	 * @param req
	 *  消息事件数据。
	 * @return
	 *  处理结果。
	 * */
	private BaseRespMessage buildMessageEventHandlers(Context context,Map<String, String> req){
		logger.info("开始事件消息解析...");
		switch(req.get("Event")){
			//菜单按钮事件。
			case Context.EVENT_MESSAGE_TYPE_CLICK:{
				logger.info("解析为菜单点击事件处理。");
				ClickEventMessage clickEvent =  EventMessageHelper.parseClickEventMessage(req);
				context.setLastMenuKey(clickEvent.getEventKey());
				logger.info("更新上下文菜单记录，并更新上下文");
				this.contextService.update(context);
				logger.info("交付消息工厂处理。");
				return this.messageParse.messageHandler(context,clickEvent);
			}
			//上传地理位置事件。
			case Context.EVENT_MESSAGE_TYPE_LOCATION:{
				logger.info("解析为上传地理位置事件处理。");
				return this.messageParse.messageHandler(context, EventMessageHelper.parseLocationEventMessage(req));
			}
			//扫描事件。
			case Context.EVENT_MESSAGE_TYPE_SCAN:{
				logger.info("解析为扫描事件处理。");
				return this.messageParse.messageHandler(context, EventMessageHelper.parseScanEventMessage(req));
			}
			//关注事件。
			case Context.EVENT_MESSAGE_TYPE_SUBSCRIBE:{
				logger.info("解析为关注事件处理。");
				context.setLastMenuKey(null);
				return this.messageParse.messageHandler(context, EventMessageHelper.parseSubscribeEventMessage(req));
			}
			//取消关注。
			case Context.EVENT_MESSAGE_TYPE_UNSUBSCRIBE:{
				logger.info("解析为取消关注事件处理。");
				context.setLastMenuKey(null);
				logger.info("置空上下文菜单值，并删除上下文缓存。");
				this.contextService.remove(context);
				return this.messageParse.messageHandler(context, EventMessageHelper.parseEventMessage(req));
			}
		}
		return null;
	}
	/**
	 * 绑定公众号注册信息。
	 * @param reqText
	 *  文本请求消息。
	 * */
	private String bindAccountRegister(TextReqMessage reqText){
		String regex =  this.bindAccountOrderPattern, data = reqText.getContent().trim(), msg = null;
		logger.info("绑定公众号注册信息...");
		logger.info("bindOrderPattern:" + regex);
		logger.info("source:" + data);
		Matcher m = Pattern.compile(regex).matcher(data);
		if(!m.find()){
			logger.info(msg = "正则表达式('"+regex+"')未能匹配('"+ data+"')数据。");
			return this.createBackTextMessage(reqText, "注册公众号失败：" + msg);
		}
		String account = m.group(1);
		logger.info("匹配出的公众号:" + account);
		Account a = this.accountDao.findAccount(account);
		if(a == null){
			logger.error(msg = "［"+ account +"］未有注册登记信息！");
			return this.createBackTextMessage(reqText, msg);
		}
		if(!StringUtils.isEmpty(a.getOpenId())){
			a.setOpenId(reqText.getToUserName());
			this.accountDao.update(a);
			logger.info(msg = "["+ a.getName() +"]与公众号OpenId["+a.getOpenId() +"]绑定成功！");
			return this.createBackTextMessage(reqText, msg);
		}
		logger.info(msg = "["+a.getName() +"]已经注册了！");
		return this.createBackTextMessage(reqText, msg);
	}
	/**
	 * 创建文本反馈消息。
	 * @param toUserOpenId
	 * 微信用户OpenId.
	 * @param fromAccountOpenId
	 * 公众号OpenId.
	 * @return
	 *  XML格式消息。
	 * */
	private String createBackTextMessage(String toUserOpenId,String fromAccountOpenId, String content){
		TextRespMessage respMessage = new TextRespMessage();
		respMessage.setToUserName(toUserOpenId);
		respMessage.setFromUserName(fromAccountOpenId);
		respMessage.setContent(content);
		return RespMesssageHelper.respMessageToXml(respMessage);
	}
	/**
	 * 创建文本反馈消息。
	 * @param req
	 * 请求消息。
	 * @return
	 *  XML格式消息。
	 * */
	private String createBackTextMessage(BaseReqMessage req, String content){
		TextRespMessage respMessage = new TextRespMessage(req);
		respMessage.setContent(content);
		return RespMesssageHelper.respMessageToXml(respMessage);
	}
}