package com.examw.wechat.service.mgr;

import com.examw.wechat.model.mgr.AccountUserInfo;
import com.examw.wechat.service.IBaseDataService;

/**
 * 微信关注用户服务。
 * @author yangyong.
 * @since 2014-04-08.
 * */
public interface IAccountUserService extends IBaseDataService<AccountUserInfo> {
	/**
	 * 加载关注状态名称。
	 * @param status
	 * @return
	 */
	String loadStatusName(Integer status);
}