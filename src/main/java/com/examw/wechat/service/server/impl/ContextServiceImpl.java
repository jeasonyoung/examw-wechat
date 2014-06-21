package com.examw.wechat.service.server.impl;

import java.util.Date;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.wechat.dao.mgr.IAccountUserDao;
import com.examw.wechat.domain.mgr.AccountUser;
import com.examw.wechat.message.Context;
import com.examw.wechat.service.server.IContextService;
/**
 * 微信用户上下文服务接口实现。
 * @author yangyong.
 * @since 2014-04-12.
 * */
public class ContextServiceImpl implements IContextService {
	private static Logger logger = Logger.getLogger(ContextServiceImpl.class);
	private IAccountUserDao accountUserDao;
	private Cache cache;
	/**
	 * 设置微信用户数据接口。
	 * @param accountUserDao
	 * 	微信用户数据接口。
	 * */
	public void setAccountUserDao(IAccountUserDao accountUserDao) {
		this.accountUserDao = accountUserDao;
	}
	/**
	 * 设置上下文缓存。
	 * @param cache
	 */
	public void setCache(Cache cache) {
		this.cache = cache;
	}
	/**
	 * 加载缓存键名。
	 * @param accountId
	 * @param userOpenId
	 * @return
	 */
	private String loadCacheKey(String accountId, String userOpenId){
		return accountId + "_" + userOpenId;
	}
	/*
	 * 获取上下文。
	 * @see com.examw.wechat.service.server.IContextService#get(java.lang.String, java.lang.String)
	 */
	@Override
	public synchronized Context get(String accountId, String userOpenId) {
		if(StringUtils.isEmpty(accountId)){
			logger.error("公众号ID(accountId)为空，无法加载上下文！");
			return null;
		}
		if(StringUtils.isEmpty(userOpenId)){
			logger.error("微信用户OpenID(userOpenId)为空，无法加载上下文！");
			return null;
		}
		String key = this.loadCacheKey(accountId, userOpenId);
		Context context = this.loadContextCache(key);
		if(context == null){
			logger.info("未找到上下文缓存，重新构建。");
			context = new Context(accountId, userOpenId);
			context.setLastActiveTime(new Date());
			//更新缓存。
			this.update(context);
		}
		if(!context.isAuthen()){//未身份认证。
			logger.info("上下文中没有身份信息，从用户信息表中加载用户信息。");
			AccountUser accountUser = this.accountUserDao.loadUser(accountId, userOpenId);
			if(accountUser != null && accountUser.getRegister() != null){
				logger.info("加载关联用户[openid:"+ accountUser.getOpenId() +"]:" + accountUser.getRegister().getName());
				context.setUserId(accountUser.getRegister().getId());
				//更新缓存
				this.update(context);
			}else {
				logger.info("该用户未身份验证过，找不到用户信息！");
			}
		}
		return context;
	}
	/**
	 * 从缓存中加载上下文。
	 * @param key
	 * @return
	 */
	private synchronized Context loadContextCache(String key){
		if(StringUtils.isEmpty(key)) return null;
		Element e =  this.cache.get(key);
		if(e == null) return null;
		return (Context)e.getObjectValue();
	}
	/*
	 * 更新上下文。
	 * @see com.examw.wechat.service.server.IContextService#update(com.examw.wechat.message.Context)
	 */
	@Override
	public synchronized void update(Context context) {
 		if(context == null) return;
 		String key = this.loadCacheKey(context.getAccountId(), context.getOpenId());
 		if(StringUtils.isEmpty(key)) return;
 		this.cache.put(new Element(key, context));
		logger.info("["+key +"]加入到缓存：" + this.cache.getName());
	}
	/*
	 * 移除上下文。
	 * @see com.examw.wechat.service.server.IContextService#remove(com.examw.wechat.message.Context)
	 */
	@Override
	public synchronized void remove(Context context) {
		if(context == null) return;
		String key = this.loadCacheKey(context.getAccountId(), context.getOpenId());
		if(!StringUtils.isEmpty(key)){
			this.cache.remove(key);
		}
	}
}