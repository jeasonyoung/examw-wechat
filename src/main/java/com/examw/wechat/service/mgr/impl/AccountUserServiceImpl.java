package com.examw.wechat.service.mgr.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.wechat.dao.account.IAccountDao;
import com.examw.wechat.dao.mgr.IAccountUserDao;
import com.examw.wechat.dao.mgr.IRegisterDao;
import com.examw.wechat.domain.account.Account;
import com.examw.wechat.domain.mgr.AccountUser;
import com.examw.wechat.domain.mgr.Register;
import com.examw.wechat.model.mgr.AccountUserInfo;
import com.examw.wechat.service.impl.BaseDataServiceImpl;
import com.examw.wechat.service.mgr.IAccountUserService;
/**
 * 微信关注用户服务实现类。
 * @author yangyong.
 * @since 2014-04-08.
 * */
public class AccountUserServiceImpl extends BaseDataServiceImpl<AccountUser, AccountUserInfo> implements IAccountUserService {
	private IAccountUserDao accountUserDao;
	private IAccountDao accountDao;
	private IRegisterDao registerDao;
	private Map<Integer, String> statusMap;
	/**
	 * 设置微信关注用户数据接口。
	 * @param accountUserDao
	 * 微信关注用户数据访问接口。
	 * */
	public void setAccountUserDao(IAccountUserDao accountUserDao) {
		this.accountUserDao = accountUserDao;
	}
	/**
	 * 设置微信公众号数据接口。
	 * @param accountDao
	 * 微信公众号数据接口。
	 * */
	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}
	/**
	 * 设置登记信息数据接口。
	 * @param registerDao
	 * 登记信息数据接口。
	 */
	public void setRegisterDao(IRegisterDao registerDao) {
		this.registerDao = registerDao;
	}
	/**
	 * 设置用户关注状态。
	 * @param statusMap
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		this.statusMap = statusMap;
	}
	/*
	 * 加载用户关注状态名称。
	 * @see com.examw.wechat.service.mgr.IAccountUserService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(this.statusMap == null || this.statusMap.size() == 0 || status == null) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 查询数据。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<AccountUser> find(AccountUserInfo info) {
		return this.accountUserDao.findUsers(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected AccountUserInfo changeModel(AccountUser data) {
		if(data == null) return null;
		AccountUserInfo info = new AccountUserInfo();
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
	protected Long total(AccountUserInfo info) {
		return this.accountUserDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public AccountUserInfo update(AccountUserInfo info) {
		if(info == null) return null;
		boolean isAdded = false;
		AccountUser data = this.accountUserDao.load(AccountUser.class, info);
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
				info.setCreateTime(new Date());
				info.setLastTime(new Date());
			}
			data = new AccountUser();
		}
		if(!isAdded){
			if(data.getCreateTime() != null) info.setCreateTime(data.getCreateTime());
			info.setLastTime(new Date());
		}
		BeanUtils.copyProperties(info, data);
		if(!StringUtils.isEmpty(info.getAccountId()) && (data.getAccount() == null || !data.getAccount().getId().equalsIgnoreCase(info.getAccountId()))){
			Account account = this.accountDao.load(Account.class, info.getAccountId());
			if(account != null) data.setAccount(account);
		}
		if(!StringUtils.isEmpty(info.getRegisterId()) && (data.getRegister() == null || !data.getRegister().getId().equalsIgnoreCase(info.getRegisterId()))){
			Register register = this.registerDao.load(Register.class, info.getRegisterId());
			if(register != null) data.setRegister(register);
		}
		if(data.getAccount() != null){
			info.setAccountName(data.getAccount().getName());
		}
		if(data.getRegister() != null){
			info.setRegisterName(data.getRegister().getName());
		}
		if(isAdded)this.accountUserDao.save(data);
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			String[] prs = ids[i].split(",");
			if(prs != null && prs.length >= 2){
				AccountUser data = this.accountUserDao.load(AccountUser.class, new AccountUserInfo(prs[0], prs[1]));
				if(data != null) this.accountUserDao.delete(data);
			}
		}
	}
}