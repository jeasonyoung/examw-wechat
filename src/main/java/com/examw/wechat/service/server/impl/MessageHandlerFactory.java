package com.examw.wechat.service.server.impl;

import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.wechat.message.BaseMessage;
import com.examw.wechat.message.Context; 
import com.examw.wechat.message.resp.BaseRespMessage;  
import com.examw.wechat.service.server.IMessageHandler;
/**
 * 消息处理实现。
 * @author yangyong.
 * @since 2014-06-20.
 */
public class MessageHandlerFactory implements IMessageHandler {
	private static final Logger logger = Logger.getLogger(MessageHandlerFactory.class);
	private static final String default_handler_key = "default";
	private Cache cache;
	private Map<String, IMessageHandler> handlers;
	/**
	 * 设置消息处理缓存。
	 * @param cache
	 */
	public void setCache(Cache cache) {
		this.cache = cache;
	}
	/**
	 * 设置消息处理集合。
	 * @param handlers
	 */
	public void setHandlers(Map<String, IMessageHandler> handlers) {
		this.handlers = handlers;
	}
	/*
	 * 消息处理。
	 * @see com.examw.wechat.service.server.IMessageHandler#handler(com.examw.wechat.message.Context, java.lang.String, com.examw.wechat.message.BaseMessage)
	 */
	@Override
	public BaseRespMessage handler(Context context, String type, BaseMessage req) {
		if(logger.isDebugEnabled()) logger.debug("消息处理工厂处理消息...");
		 if(context == null){
			 if(logger.isDebugEnabled()) logger.debug("上下文不存在！");
			 return null;
		 }
		 if(StringUtils.isEmpty(type)){
			 if(logger.isDebugEnabled()) logger.debug("消息类型［type］为空！");
			 return null;
		 }
		 if(req == null){
			 if(logger.isDebugEnabled()) logger.debug("消息请求对象丢失！");
			 return null;
		 }
		 if(this.handlers == null || this.handlers.size() == 0){
			String err = "未配置消息处理集合！"; 
			logger.error(err);
			throw new RuntimeException(err);
		 }
		 IMessageHandler handler =  this.loadHandler(type);
		 if(handler == null){
			 if(logger.isDebugEnabled()) logger.debug("未找到消息类型［type："+ type+"］的处理器！");
			 return null;
		 }
		return handler(context, type, req);
	}
	//查找处理器。
	private synchronized IMessageHandler loadHandler(String key){
		IMessageHandler handler = this.loadCache(key);
		if(handler == null){
			if(logger.isDebugEnabled()) logger.debug("从配置中［key:"+key+"］加载处理器!");
			handler = this.handlers.get(key);
			if(handler != null){
				this.putCache(key, handler);
			}
		}
		if(handler != null) return handler;
		return this.loadDefaultHandler();
	}
	//加载默认处理器。
	private synchronized IMessageHandler loadDefaultHandler(){
		if(logger.isDebugEnabled()) logger.debug("准备加载默认处理器缓存cache!");
		IMessageHandler handler = this.loadCache(default_handler_key);
		if (handler == null) {
			handler = this.handlers.get(default_handler_key);
			if(handler == null){
				String err = "未配置默认消息处理器［key:"+default_handler_key+"］！";
				logger.error(err);
				throw new RuntimeException(err);
			}
			this.putCache(default_handler_key, handler);
		}
		return handler;
	}
	//加载缓存。
	private synchronized IMessageHandler loadCache(String key){
		if(logger.isDebugEnabled()) logger.debug("准备加载缓存cache!");
		if(StringUtils.isEmpty(key)){
			if(logger.isDebugEnabled()) logger.debug("缓存键key参数为空!");
			return null;
		}
		if(this.cache == null){
			if(logger.isDebugEnabled()) logger.debug("未配置缓存cache!");
			return null;
		}
		Element e = this.cache.get(key);
		if(e == null){
			if(logger.isDebugEnabled()) logger.debug("未找到［key："+key+"］的处理器缓存!");
		}
		if(logger.isDebugEnabled()) logger.debug("已找到［key："+key+"］的处理器缓存。");
		return (IMessageHandler)e.getObjectValue();
	}
	//放入缓存
	private synchronized void putCache(String key, IMessageHandler handler){
		if(logger.isDebugEnabled()) logger.debug("准备压入缓存cache!");
		if(StringUtils.isEmpty(key)){
			if(logger.isDebugEnabled()) logger.debug("缓存键key参数为空!");
			return;
		}
		if(this.cache == null){
			if(logger.isDebugEnabled()) logger.debug("未配置缓存cache!");
			return;
		}
		if(handler == null){
			if(logger.isDebugEnabled()) logger.debug("键［"+key+"］处理器为null!");
			return;
		}
		this.cache.put(new Element(key, handler));
		if(logger.isDebugEnabled()) logger.debug("键［"+key+"］处理器放入缓存cache!");
	}
}