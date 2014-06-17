package com.examw.wechat.dao.mgr;

import java.util.List;

import com.examw.wechat.dao.IBaseDao;
import com.examw.wechat.domain.mgr.AccountUser;
import com.examw.wechat.model.mgr.AccountUserInfo;


/**
 * 微信关注用户数据访问接口。
 * @author yangyong.
 * @since 2014-04-08.
 * */
public interface IAccountUserDao extends IBaseDao<AccountUser> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据集合。
	 * */
	List<AccountUser> findUsers(AccountUserInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 * */
	Long total(AccountUserInfo info);
	/**
	 * 加载微信关注用户。
	 * @param accountId
	 * 微信公众号ID。
	 * @param openId
	 * 关注用户微信ID。
	 * @return
	 * 微信关注用户信息。
	 * */
	AccountUser loadUser(String accountId,String openId);
}