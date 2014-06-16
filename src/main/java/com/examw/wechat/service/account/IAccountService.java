package com.examw.wechat.service.account;

import java.util.List;

import com.examw.service.IDataService;
import com.examw.wechat.model.account.AccountInfo;
/**
 * 微信公众账号服务接口。
 * @author yangyong.
 * @since 2014-04-01.
 * */
public interface IAccountService extends IDataService<AccountInfo> {
	/**
	 * 加载公众号状态名称。
	 * @param status
	 * @return
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载公众号类型名称。
	 * @param type
	 * @return
	 */
	String loadTypeName(Integer type);
	/**
	 * 加载全部的公众号。
	 * @return 全部的公众号。
	 * */
	List<AccountInfo> loadAllAccounts();
}