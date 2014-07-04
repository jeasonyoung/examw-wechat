package com.examw.wechat.service.server.impl;
 
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.wechat.dao.account.IAccountDao;
import com.examw.wechat.domain.account.Account;
import com.examw.wechat.message.Context;
import com.examw.wechat.message.events.ClickEventMessage;
import com.examw.wechat.message.events.EventMessageHelper;
import com.examw.wechat.message.events.ViewEventMessage;
import com.examw.wechat.message.req.BaseReqMessage;
import com.examw.wechat.message.req.ReqMessageHelper;
import com.examw.wechat.message.req.TextReqMessage;
import com.examw.wechat.message.resp.BaseRespMessage;
import com.examw.wechat.message.resp.RespMesssageHelper;
import com.examw.wechat.message.resp.TextRespMessage;
import com.examw.wechat.service.mgr.IRecordService;
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
	private static final Logger logger = Logger.getLogger(DefaultCoreServiceImpl.class);
	private IAccountDao accountDao;
	private IContextService contextService;
	private IRecordService recordService;
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
	 * 设置消息记录服务接口。
	 * @param recordService
	 * 消息记录服务接口。
	 */
	public void setRecordService(IRecordService recordService) {
		this.recordService = recordService;
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
		if(logger.isDebugEnabled()) logger.debug("开始校验微信接入令牌...");
		String err = null;
		List<Account> accounts = this.accountDao.loadAllAccounts();
		if(accounts == null || accounts.size() == 0){
			logger.error(err = "系统中未登记任何微信公众账号信息！");
			throw new RuntimeException(err);
		}
		for(Account account: accounts){
			if(account == null || account.getStatus() == Account.ACCOUNT_STATUS_DISABLE) continue;
			if(StringUtils.isEmpty(account.getToken())) continue;
			if(SignUtil.checkSignature(signature, account.getToken(), timestamp, nonce)){
				if(logger.isDebugEnabled()) logger.debug("token:" + account.getToken());
				if(logger.isDebugEnabled()) logger.debug("校验微信接入令牌结束！");
				return true;
			}
		}
		logger.error(err = "校验微信接入令牌失败！");
		return false;
	}
	/*
	 * 处理微信请求。
	 * @see com.examw.wechat.service.server.ICoreService#processRequest(java.util.Map)
	 */
	@Override
	public synchronized String processRequest(Map<String, String> dataMap) {
		Context context = null;
		String callback = null;
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
			if(logger.isDebugEnabled()) logger.debug("加载上下文...");
			context = this.contextService.get(account.getId(), user_openId);
			if(context == null){
				logger.error("加载上下文失败！");
				return null;
			}
			BaseRespMessage resp = null;
			//消息处理
 			switch(msg_type){
 				//事件消息。
				case Context.REQ_MESSAGE_TYPE_EVENT: {
					if(logger.isDebugEnabled()) logger.debug("解析为事件消息处理。");
					resp = this.buildMessageEventHandlers(context, dataMap);
				}
				break;
				//图片消息。
				case Context.REQ_MESSAGE_TYPE_IMAGE:{
					if(logger.isDebugEnabled()) logger.debug("解析为图片消息处理。");
					resp = this.messageParse.messageHandler(context, ReqMessageHelper.parseImageReqMessage(dataMap));
				}
				break;
				//链接消息。
				case Context.REQ_MESSAGE_TYPE_LINK:{
					if(logger.isDebugEnabled()) logger.debug("解析为链接消息处理。");
					resp = this.messageParse.messageHandler(context, ReqMessageHelper.parseLinkReqMessage(dataMap));
				}
				break;
				//地理位置消息。
				case Context.REQ_MESSAGE_TYPE_LOCATION:{
					if(logger.isDebugEnabled()) logger.debug("解析为地理位置消息处理。");
					resp = this.messageParse.messageHandler(context, ReqMessageHelper.parseLocationReqMessage(dataMap));
				}
				break;
				//文本消息。
				case Context.REQ_MESSAGE_TYPE_TEXT:{
					if(logger.isDebugEnabled()) logger.debug("解析为文本消息处理。");
					resp = this.messageParse.messageHandler(context, ReqMessageHelper.parseTextReqMessage(dataMap));
				}
				break;
				//视频消息。
				case Context.REQ_MESSAGE_TYPE_VIDEO:{
					if(logger.isDebugEnabled()) logger.debug("解析为视频消息处理。");
					resp = this.messageParse.messageHandler(context, ReqMessageHelper.parseVideoReqMessage(dataMap));
				}
				break;
				//语音消息。
				case Context.REQ_MESSAGE_TYPE_VOICE:{
					if(logger.isDebugEnabled()) logger.debug("解析为语音消息处理。");
					resp = this.messageParse.messageHandler(context, ReqMessageHelper.parseVoiceReqMessage(dataMap));
				}
				break;
			}
			if(resp != null){
				this.contextService.update(context);
				callback =  RespMesssageHelper.respMessageToXml(resp);
			}
		} catch (Exception e) {
			if(logger.isDebugEnabled()) logger.debug("处理微信请求时发生异常:" + e.getMessage());
			logger.error(e);
			e.printStackTrace();
		}finally{
			//消息记录。
			this.recordMessager(context, dataMap);
		}
		return callback;
	}
	//构建消息事件处理。
	private BaseRespMessage buildMessageEventHandlers(Context context,Map<String, String> req){
		if(logger.isDebugEnabled()) logger.debug("开始事件消息解析...");
		switch(req.get("Event")){
			//菜单按钮事件。
			case Context.EVENT_MESSAGE_TYPE_CLICK:{
				if(logger.isDebugEnabled()) logger.debug("解析为菜单点击事件处理。");
				ClickEventMessage clickEvent =  EventMessageHelper.parseClickEventMessage(req);
				context.setLastMenuKey(clickEvent.getEventKey());
				if(logger.isDebugEnabled()) logger.debug("更新上下文菜单记录，并更新上下文");
				this.contextService.update(context);
				if(logger.isDebugEnabled()) logger.debug("交付消息工厂处理。");
				return this.messageParse.messageHandler(context,clickEvent);
			}
			//菜单链接跳转事件。
			case  Context.EVENT_MESSAGE_TYPE_VIEW:{
				if(logger.isDebugEnabled()) logger.debug("解析为菜单链接跳转事件处理。");
				ViewEventMessage viewEvent =  EventMessageHelper.parseViewEventMessage(req);
				context.setLastMenuKey(viewEvent.getEventKey());
				if(logger.isDebugEnabled()) logger.debug("更新上下文菜单记录，并更新上下文");
				this.contextService.update(context);
				if(logger.isDebugEnabled()) logger.debug("交付消息工厂处理。");
				return this.messageParse.messageHandler(context,viewEvent);
			}
			//上传地理位置事件。
			case Context.EVENT_MESSAGE_TYPE_LOCATION:{
				if(logger.isDebugEnabled()) logger.debug("解析为上传地理位置事件处理。");
				return this.messageParse.messageHandler(context, EventMessageHelper.parseLocationEventMessage(req));
			}
			//扫描事件。
			case Context.EVENT_MESSAGE_TYPE_SCAN:{
				if(logger.isDebugEnabled()) logger.debug("解析为扫描事件处理。");
				return this.messageParse.messageHandler(context, EventMessageHelper.parseScanEventMessage(req));
			}
			//关注事件。
			case Context.EVENT_MESSAGE_TYPE_SUBSCRIBE:{
				if(logger.isDebugEnabled()) logger.debug("解析为关注事件处理。");
				context.setLastMenuKey(null);
				return this.messageParse.messageHandler(context, EventMessageHelper.parseSubscribeEventMessage(req));
			}
			//取消关注。
			case Context.EVENT_MESSAGE_TYPE_UNSUBSCRIBE:{
				if(logger.isDebugEnabled()) logger.debug("解析为取消关注事件处理。");
				context.setLastMenuKey(null);
				if(logger.isDebugEnabled()) logger.debug("置空上下文菜单值，并删除上下文缓存。");
				this.contextService.remove(context);
				return this.messageParse.messageHandler(context, EventMessageHelper.parseEventMessage(req));
			}
		}
		return null;
	}
	//绑定公众号注册信息。
	private String bindAccountRegister(TextReqMessage reqText){
		String regex =  this.bindAccountOrderPattern, data = reqText.getContent().trim(), msg = null;
		if(logger.isDebugEnabled()) logger.debug("绑定公众号注册信息...");
		if(logger.isDebugEnabled()) logger.debug("bindOrderPattern:" + regex);
		if(logger.isDebugEnabled()) logger.debug("source:" + data);
		Matcher m = Pattern.compile(regex).matcher(data);
		if(!m.find()){
			if(logger.isDebugEnabled()) logger.debug(msg = "正则表达式('"+regex+"')未能匹配('"+ data+"')数据。");
			return this.createBackTextMessage(reqText, "注册公众号失败：" + msg);
		}
		String account = m.group(1);
		if(logger.isDebugEnabled()) logger.debug("匹配出的公众号:" + account);
		Account a = this.accountDao.findAccount(account);
		if(a == null){
			logger.error(msg = "［"+ account +"］未有注册登记信息！");
			return this.createBackTextMessage(reqText, msg);
		}
		if(!StringUtils.isEmpty(a.getOpenId())){
			a.setOpenId(reqText.getToUserName());
			this.accountDao.update(a);
			if(logger.isDebugEnabled()) logger.debug(msg = "["+ a.getName() +"]与公众号OpenId["+a.getOpenId() +"]绑定成功！");
			return this.createBackTextMessage(reqText, msg);
		}
		if(logger.isDebugEnabled()) logger.debug(msg = "["+a.getName() +"]已经注册了！");
		return this.createBackTextMessage(reqText, msg);
	}
	//创建文本反馈消息。
	private String createBackTextMessage(String toUserOpenId,String fromAccountOpenId, String content){
		TextRespMessage respMessage = new TextRespMessage();
		respMessage.setToUserName(toUserOpenId);
		respMessage.setFromUserName(fromAccountOpenId);
		respMessage.setContent(content);
		return RespMesssageHelper.respMessageToXml(respMessage);
	}
	//创建文本反馈消息。
	private String createBackTextMessage(BaseReqMessage req, String content){
		TextRespMessage respMessage = new TextRespMessage(req);
		respMessage.setContent(content);
		return RespMesssageHelper.respMessageToXml(respMessage);
	}
	//消息记录。
	private synchronized void recordMessager(Context context, Map<String, String> reqMap){
		if(logger.isDebugEnabled()) logger.debug("准备开始消息记录...");
		if(context == null || reqMap == null || reqMap.size() == 0){
			if(logger.isDebugEnabled()) logger.debug("上下文或消息为空！");
			return;
		}
		StringBuilder builder = new StringBuilder();
		for(Entry<String, String> entry : reqMap.entrySet()){
			if(builder.length() > 0) builder.append(",");
			builder.append(entry.getKey()).append("=").append(entry.getValue());
		}
		if(this.recordService == null){
			if(logger.isDebugEnabled()) logger.debug("消息记录服务未配置！");
			return;
		}
		this.recordService.saveRecord(context, builder.toString());
	}
}