package com.examw.wechat.dao.account;

import java.util.List;

import com.examw.wechat.dao.IBaseDao;
import com.examw.wechat.domain.account.Account;
import com.examw.wechat.model.account.AccountInfo;

/**
 * 微信公众号数据访问接口。
 * @author yangyong.
 * @since 2014-03-31.
 * */
public interface IAccountDao extends IBaseDao<Account> {
	/**
	 * 加载全部可用的公众账号。
	 * @return 可用的公众账号。
	 * */
	List<Account> loadAllAccounts();
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 *  结果数据集。
	 * */
	List<Account> findAccounts(AccountInfo info);
	/**
	 * 查询数据总数。
	 * @param	info
	 * 	查询条件。
	 * @return
	 * 数据总数。
	 * */
	Long total(AccountInfo info);
	/**
	 * 根据OpenId来加载公众账号。
	 * @param openId
	 * 		OpenId
	 * @return
	 * 	加载公众账号。
	 * */
	Account loadAccount(String openId);
	/**
	 * 根据公众号加载注册信息。
	 * @param account
	 *  公众号账号。
	 * @return
	 * 公众号注册信息。
	 * */
	Account findAccount(String account);
}