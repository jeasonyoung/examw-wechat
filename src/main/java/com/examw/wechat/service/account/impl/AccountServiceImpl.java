package com.examw.wechat.service.account.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils; 

import com.examw.wechat.dao.account.IAccountDao;
import com.examw.wechat.domain.account.Account;
import com.examw.wechat.model.account.AccountInfo;
import com.examw.wechat.service.account.IAccountService;
import com.examw.wechat.service.impl.BaseDataServiceImpl;
/**
 * 微信公众账号服务实现类。
 * @author yangyong.
 * @since 2014-04-01.
 * */
public class AccountServiceImpl extends BaseDataServiceImpl<Account, AccountInfo> implements IAccountService {
	private IAccountDao accountDao;
	private Map<Integer, String> statusMap,typesMap;
	/**
	 * 设置公众号数据数据接口。
	 * @param accountDao
	 */
	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}
	/**
	 * 设置公众号状态集合。
	 * @param status
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		this.statusMap = statusMap;
	}
	/**
	 * 设置公众号类型集合。
	 * @param typesMap
	 */
	public void setTypesMap(Map<Integer, String> typesMap) {
		this.typesMap = typesMap;
	}
	/*
	 * 加载公众号状态名称。
	 * @see com.examw.wechat.service.account.IAccountService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(this.statusMap == null || status == null) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 加载公众号类型名称。
	 * @see com.examw.wechat.service.account.IAccountService#loadTypeName(java.lang.Integer)
	 */
	@Override
	public String loadTypeName(Integer type) {
		if(this.typesMap == null || type == null) return null;
		return this.typesMap.get(type);
	}
	/*
	 * 加载全部公众号。
	 * @see com.examw.wechat.service.account.IAccountService#loadAllAccounts()
	 */
	@Override
	public List<AccountInfo> loadAllAccounts() {
		List<Account> list  = this.accountDao.loadAllAccounts();
		if(list == null || list.size() == 0) return null;
		List<AccountInfo> results = new ArrayList<>();
		for(int i = 0; i < list.size(); i++){
			AccountInfo info = this.changeModel(list.get(i));
			if(info != null) results.add(info);
		}
		return results;
	}
	/*
	 * 查询数据。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Account> find(AccountInfo info) {
		return this.accountDao.findAccounts(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected AccountInfo changeModel(Account data) {
		if(data == null) return null;
		AccountInfo info = new AccountInfo();
		BeanUtils.copyProperties(data, info);
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(AccountInfo info) {
		return this.accountDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public AccountInfo update(AccountInfo info) {
		if(info != null){
			boolean isAdded = false;
			Account data = (info.getId() == null || info.getId().trim().isEmpty()) ?  null : this.accountDao.load(Account.class, info.getId());
			if(isAdded = (data == null)){
				if(info.getId() == null || info.getId().trim().isEmpty()){
					info.setId(UUID.randomUUID().toString());
				}
				data = new Account();
			}
			BeanUtils.copyProperties(info, data);
			if(isAdded) this.accountDao.save(data);
		}
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0)return;
		for(int i = 0; i < ids.length;i++){
			if(ids[i] == null || ids[i].trim().isEmpty()) continue;
			Account data = this.accountDao.load(Account.class, ids[i]);
			if(data != null)this.accountDao.delete(data);
		}
	}
}