package com.examw.wechat.service.shiro;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

/**
 * 重试限制匹配器。
 * @author yangyong.
 * @since 2014-05-13.
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
	private static final String PASSWORDRETRYCACHE = "passwordRetryCache";
	private static final int RETRY_MAX_COUNT = 5;
	private Cache<String,AtomicInteger> passwordRetryCache;
	/**
	 * 构造函数。
	 * @param cacheManager
	 * 缓存管理器。
	 */
	public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager){
		this.passwordRetryCache = cacheManager.getCache(PASSWORDRETRYCACHE);
	}
	/*
	 * 执行凭证匹配。
	 * @see org.apache.shiro.authc.credential.HashedCredentialsMatcher#doCredentialsMatch(org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.authc.AuthenticationInfo)
	 */
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token,AuthenticationInfo info){
		String account = (String)token.getPrincipal();
		//retry count+1
		AtomicInteger retryCount = this.passwordRetryCache.get(account);
		if(retryCount == null){
			retryCount = new AtomicInteger(0);
			this.passwordRetryCache.put(account, retryCount);
		}
		
		if(retryCount.incrementAndGet() > RETRY_MAX_COUNT){
			//if retry count > 5 throw
			throw new ExcessiveAttemptsException();
		}
		
		boolean matches = super.doCredentialsMatch(token, info);
		if(matches){
			//clear retry count
			this.passwordRetryCache.remove(account);
		}
		return matches;
	}
}