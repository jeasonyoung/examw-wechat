package com.examw.wechat.dao.mgr.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.wechat.dao.impl.BaseDaoImpl;
import com.examw.wechat.dao.mgr.IAccountUserDao;
import com.examw.wechat.domain.mgr.AccountUser;
import com.examw.wechat.model.mgr.AccountUserInfo;
/**
 * 微信关注用户数据实现类。
 * @author yangyong.
 * @since 2014-04-08.
 * */
public class AccountUserDaoImpl extends BaseDaoImpl<AccountUser> implements IAccountUserDao {
	/*
	 * 查询数据。
	 * @see com.examw.wechat.dao.mgr.IAccountUserDao#findUsers(com.examw.wechat.model.mgr.AccountUserInfo)
	 */
	@Override
	public List<AccountUser> findUsers(AccountUserInfo info) {
		String hql = "from AccountUser a where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(info.getSort().equalsIgnoreCase("accountName")){
				info.setSort("account.name");
			}
			hql += " order by a." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.wechat.dao.mgr.IAccountUserDao#total(com.examw.wechat.model.mgr.AccountUserInfo)
	 */
	@Override
	public Long total(AccountUserInfo info) {
		String hql = "select count(*) from AccountUser a where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	/**
	 * 添加查询条件。
	 * @param info
	 * 查询条件。
	 * @param hql
	 * HQL.
	 * @param parameters
	 * 查询参数。
	 * @return HQL
	 * */
	protected String addWhere(AccountUserInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getAccountId())){
			hql += " and (a.account.id = :accountId) ";
			parameters.put("accountId", info.getAccountId());
		}
		if(!StringUtils.isEmpty(info.getAccountName())){
			hql += " and (a.account.name like :accountName or a.account.account like :accountName) ";
			parameters.put("accountName", "%" + info.getAccountName() + "%");
		}
		if(!StringUtils.isEmpty(info.getUserName())){
			hql += " and (a.userName like :userName or a.userSign like :userName) ";
			parameters.put("userName", "%"+ info.getUserName() +"%");
		}
		if(!StringUtils.isEmpty(info.getOpenId())){
			hql += " and (a.openId = :openId)";
			parameters.put("openId", info.getOpenId());
		}
		if(info.getStatus() != null && info.getStatus() != 0){
			hql += " and (a.status = :status)";
			parameters.put("status", info.getStatus());
		}
		return hql;
	}
	/**
	 * 重载加载实体数据。
	 * @param c
	 * 实体数据类型。
	 * @param id
	 * 带联合主键的WeChatUserInfo对象。
	 * @return
	 * 实体对象。
	 * */
	@Override
	public AccountUser load(Class<AccountUser> c, Serializable id){
		if(id == null) return null;
		if(id instanceof AccountUserInfo){
			AccountUserInfo info = (AccountUserInfo)id;
			return this.loadUser(info.getAccountId(), info.getOpenId());
		}
		return null;
	}
	/*
	 * 加载关注用户。
	 * @see com.examw.wechat.dao.mgr.IAccountUserDao#loadUser(java.lang.String, java.lang.String)
	 */
	@Override
	public AccountUser loadUser(String accountId, String openId) {
		final String hql = "from AccountUser a where a.openId = :openId and a.account.id = :accountId order by a.lastTime desc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("accountId", accountId);
		parameters.put("openId", openId);
		List<AccountUser> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
}