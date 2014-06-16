package com.examw.wechat.dao.account.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.examw.wechat.dao.account.IAccountDao;
import com.examw.wechat.dao.impl.BaseDaoImpl;
import com.examw.wechat.domain.account.Account;
import com.examw.wechat.model.account.AccountInfo;

/**
 * 微信公众账号数据访问实现类。
 * @author yangyong.
 * @since 2014-03-31.
 * */
public class AccountDaoImpl extends BaseDaoImpl<Account> implements IAccountDao {
	/*
	 * 根据OpenId加载公众号。
	 * @see com.examw.wechat.dao.account.IAccountDao#loadAccount(java.lang.String)
	 */
	@Override
	public Account loadAccount(String openId) {
		if(openId == null || openId.trim().isEmpty()) return null;
		final String hql = "from Account a where a.openId = :openId order by a.createTime desc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("openId", openId);
		List<Account> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
	/*
	 * 加载全部公众号。
	 * @see com.examw.wechat.dao.account.IAccountDao#loadAllAccounts()
	 */
	@Override
	public List<Account> loadAllAccounts() {
		final String hql = "from Account a order by a.name";
		return this.find(hql, null, null, null);
	}
	/*
	 * 查询数据。
	 * @see com.examw.wechat.dao.account.IAccountDao#findAccounts(com.examw.wechat.model.account.AccountInfo)
	 */
	@Override
	public List<Account> findAccounts(AccountInfo info) {
		String hql = "from Account a where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters);
		if(info.getSort() != null && !info.getSort().trim().isEmpty()){
			hql += " order by a." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.wechat.dao.account.IAccountDao#total(com.examw.wechat.model.account.AccountInfo)
	 */
	@Override
	public Long total(AccountInfo info) {
		String hql = "select count(*) from Account a where 1=1 ";
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
	protected String addWhere(AccountInfo info, String hql,Map<String, Object> parameters) {
		if(info.getAccount() != null && !info.getAccount().trim().isEmpty()){
			hql += " and (a.name like :account or a.account like :account) ";
			parameters.put("account", "%" +info.getAccount() + "%");
		}
		if(info.getStatus() != null && info.getStatus() >= 0){
			hql += " and (a.status = :status) ";
			parameters.put("status", info.getStatus());
		}
		if(info.getType() != null && info.getType() >= 0){
			hql += " and (a.type = :type) ";
			parameters.put("type", info.getType());
		}
		return hql;
	}
	/*
	 * 根据公众号账号查询公众号。
	 * @see com.examw.wechat.dao.account.IAccountDao#findAccount(java.lang.String)
	 */
	@Override
	public Account findAccount(String account) {
		if(account == null || account.trim().isEmpty()) return null;
		final String hql = "from Account a where a.account = :account order by a.createTime desc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("account", account);
		List<Account> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
}