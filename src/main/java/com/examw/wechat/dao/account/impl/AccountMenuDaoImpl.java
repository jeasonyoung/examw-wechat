package com.examw.wechat.dao.account.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.examw.wechat.dao.account.IAccountMenuDao;
import com.examw.wechat.dao.impl.BaseDaoImpl;
import com.examw.wechat.domain.account.AccountMenu;

/**
 * 微信公众号菜单数据访问实现类。
 * @author yangyong.
 * @since 2014-04-02.
 * */
public class AccountMenuDaoImpl  extends BaseDaoImpl<AccountMenu> implements IAccountMenuDao {
	/*
	 * 查询数据。
	 * @see com.examw.wechat.dao.account.IAccountMenuDao#findMenus(java.lang.String)
	 */
	@Override
	public List<AccountMenu> findMenus(String accountId) {
		String hql = "from AccountMenu a  where a.parent = null ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		if(accountId != null && !accountId.trim().isEmpty()){
			hql += "  and a.account.id = :accountId  ";
			parameters.put("accountId", accountId);
		}
		hql += " order by a.orderNo";
		return this.find(hql, parameters, null, null);
	}
	/*
	 * 加载菜单。
	 * @see com.examw.wechat.dao.account.IAccountMenuDao#loadMenu(java.lang.String, java.lang.String)
	 */
	@Override
	public AccountMenu loadMenu(String accountId,String code) {
		if(accountId == null || accountId.trim().isEmpty()) return null;
		if(code == null || code.trim().isEmpty()) return null;
		final String hql = "from AccountMenu a where a.account.id = :accountId and a.code = :code";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("accountId", accountId);
		parameters.put("code", code);
		List<AccountMenu> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
	/*
	 * 删除数据。
	 * @see com.examw.wechat.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(AccountMenu data){
		if(data == null) return;
		if(data.getChildren() != null){
			for(AccountMenu child : data.getChildren()){
				if(child == null) continue;
				this.delete(child);
			}
		}
		super.delete(data);
	}
}