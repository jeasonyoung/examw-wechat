package com.examw.wechat.service.account.impl;

import java.util.Calendar;
import java.util.Date; 
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.wechat.dao.account.IAccessTokenDao;
import com.examw.wechat.dao.account.IAccountDao;
import com.examw.wechat.domain.account.AccessToken;
import com.examw.wechat.domain.account.Account;
import com.examw.wechat.model.account.AccessTokenInfo;
import com.examw.wechat.service.account.IAccessTokenService;
import com.examw.wechat.service.impl.BaseDataServiceImpl;
import com.examw.wechat.support.HttpUtil;
/**
 * 公众号全局唯一票据服务实现类。
 * @author yangyong.
 * @since 2014-05-16.
 */
public class AccessTokenServiceImpl  extends BaseDataServiceImpl<AccessToken, AccessTokenInfo> implements IAccessTokenService  {
	private static Logger logger = Logger.getLogger(AccessTokenServiceImpl.class);
	private IAccessTokenDao accessTokenDao;
	private IAccountDao accountDao;
	private String accessTokenUrl;
	/**
	 * 设置票据数据接口。
	 * @param accessTokenDao
	 * 票据数据接口。
	 */
	public void setAccessTokenDao(IAccessTokenDao accessTokenDao) {
		this.accessTokenDao = accessTokenDao;
	}
	/**
	 * 设置公众号数据接口。
	 * @param accountDao
	 */
	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}
	/**
	 * 设置票据URL。
	 * @param accessTokenUrl
	 * 票据URL。
	 */
	public void setAccessTokenUrl(String accessTokenUrl) {
		this.accessTokenUrl = accessTokenUrl;
	}
	/*
	 * 加载票据。
	 * @see com.examw.wechat.service.server.IAccessTokenService#loadAccessToken(java.lang.String)
	 */
	@Override
	public String loadAccessToken(String accountId) throws Exception {
		if(accountId == null || accountId.trim().isEmpty()) {
			logger.error("公众号全局唯一票据时未传入公众号ID！");
			return null;
		}
		AccessToken token = this.accessTokenDao.loadAccessToken(accountId);
		if(token == null || !token.isEffective())
			return this.refreshAccessToken(accountId);
		return token.getAccessToken();
	}
	/*
	 * 刷新票据。
	 * @see com.examw.wechat.service.server.IAccessTokenService#refreshAccessToken(java.lang.String)
	 */
	@Override
	public synchronized String refreshAccessToken(String accountId) throws Exception {
		if(StringUtils.isEmpty(accountId)) return null;
		String error = null;
		Account account = this.accountDao.load(Account.class, accountId);
		if(account == null || StringUtils.isEmpty(account.getAppId())){
			logger.error(error = "未找到公众号信息或未配置第三方用户凭证信息！");
			throw new Exception(error);
		}
		if(StringUtils.isEmpty(this.accessTokenUrl)){
			logger.error(error = "未注入tokenUrl!");
			throw new Exception(error);
		}
		String url = String.format(this.accessTokenUrl, account.getAppId(), account.getAppSecret());
		logger.info("token请求url:" + url);
		String json = HttpUtil.httpsRequest(url, "GET", null);
		if(StringUtils.isEmpty(json)){
			logger.error(error = "未得到微信服务器的反馈！");
			throw new Exception(json);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		 
		@SuppressWarnings("unchecked")
		Map<String, Object> tokenMap  = mapper.readValue(json, Map.class);
		String access_token = (String)tokenMap.get("access_token");
		Integer expires_in = (Integer)tokenMap.get("expires_in");
		if(StringUtils.isEmpty(access_token)){
			logger.error(error = "获取微信服务反馈令牌异常："  + json);
			throw new Exception(error);
		}
		logger.info("获取[" + account.getName() + "]公众号令牌:" + access_token);
		AccessToken token = new AccessToken();
		token.setId(UUID.randomUUID().toString());
		token.setAccount(account);
		token.setAccessToken(access_token);
		token.setCreateTime(new Date());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(token.getCreateTime());
		calendar.add(Calendar.SECOND, expires_in);
		token.setFailureTime(calendar.getTime());
		this.accessTokenDao.save(token);
		return token.getAccessToken();
	}
	/*
	 * 查询数据。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<AccessToken> find(AccessTokenInfo info) {
		return this.accessTokenDao.findAccessTokens(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected AccessTokenInfo changeModel(AccessToken data) {
		if(data == null) return null;
		AccessTokenInfo info = new AccessTokenInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getAccount() != null){
			info.setAccountId(data.getAccount().getId());
			info.setAccountName(data.getAccount().getName());
		}
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(AccessTokenInfo info) {
		return this.accessTokenDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public AccessTokenInfo update(AccessTokenInfo info) {
		if(info == null) return null;
		AccessToken data = StringUtils.isEmpty(info.getId()) ? null : this.accessTokenDao.load(AccessToken.class, info.getId());
		boolean isAdded = false;
		if(isAdded = (data == null)){
			data = new AccessToken();
			info.setId(UUID.randomUUID().toString());
		}
		BeanUtils.copyProperties(info, data);
		if(!StringUtils.isEmpty(info.getAccountId()) && (data.getAccount() == null || !data.getAccount().getId().equalsIgnoreCase(info.getAccountId()))){
			Account account = this.accountDao.load(Account.class, info.getAccountId());
			if(account != null) data.setAccount(account);
		}
		if(data.getAccount() != null){
			info.setAccountName(data.getAccount().getName());
		}
		if(isAdded)this.accessTokenDao.save(data);
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0)return;
		for(int i = 0; i < ids.length; i++){
			if(ids[i] == null || ids[i].trim().isEmpty()) continue;
			AccessToken data = this.accessTokenDao.load(AccessToken.class, ids[i]);
			if(data != null)this.accessTokenDao.delete(data);
		}
	}
}