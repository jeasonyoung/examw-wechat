package com.examw.wechat.dao.account.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.examw.wechat.dao.account.IAccessTokenDao;
import com.examw.wechat.dao.impl.BaseDaoImpl;
import com.examw.wechat.domain.account.AccessToken;
import com.examw.wechat.model.account.AccessTokenInfo;
/**
 * 公众号全局唯一票据数据访问实现。
 * @author yangyong.
 * @since 2014-04-03.
 * */
public class AccessTokenDaoImpl  extends BaseDaoImpl<AccessToken> implements IAccessTokenDao {
	/*
	 * 查询数据。
	 * @see com.examw.wechat.dao.server.IAccessTokenDao#findAccessTokens(com.examw.wechat.model.server.AccessTokenInfo)
	 */
	@Override
	public List<AccessToken> findAccessTokens(AccessTokenInfo info) {
		String hql = "from AccessToken a where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters);
		if(info.getSort() != null && !info.getSort().trim().isEmpty()){
			if(info.getSort().equalsIgnoreCase("accountName")){
				info.setSort("account.name");
			}
			hql += " order by a." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.wechat.dao.server.IAccessTokenDao#total(com.examw.wechat.model.server.AccessTokenInfo)
	 */
	@Override
	public Long total(AccessTokenInfo info) {
		String hql = "select count(*) from AccessToken a where 1=1 ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters); 
		return this.count(hql, parameters);
	}
	/**
	 * 添加查询条件。
	 * @param info
	 * 查询条件。
	 * @param hql
	 * HQL
	 * @param parameters
	 * 参数集合。
	 * @return
	 * HQL
	 * */
	protected String addWhere(AccessTokenInfo info, String hql,Map<String, Object> parameters) {
		if(info.getAccountName() != null && !info.getAccountName().trim().isEmpty()){
			hql += " and (a.account.name like :accountName or a.account.account like :accountName) ";
			parameters.put("accountName", "%"+ info.getAccountName()+"%");
		}
		if(info.getAccessToken() != null && !info.getAccessToken().trim().isEmpty()){
			hql +=" and (a.accessToken like :accessToken) ";
			parameters.put("accessToken", "%"+ info.getAccessToken()+"%");
		}
		return hql;
	}
	/*
	 *  加载数据。
	 * @see com.examw.wechat.dao.server.IAccessTokenDao#loadAccessToken(java.lang.String)
	 */
	@Override
	public AccessToken loadAccessToken(String accountId) {
		final String hql = "from AccessToken a where a.account.id = :accountId  order by a.failureTime desc,a.createTime desc";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("accountId", accountId);
		List<AccessToken> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
}