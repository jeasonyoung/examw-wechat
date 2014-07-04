package com.examw.wechat.service.mgr;

import com.examw.wechat.domain.mgr.AccountUser;
import com.examw.wechat.message.Context;
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
	/**
	 * 添加关注用户。
	 * @param context
	 */
	AccountUser addSubscribe(Context context);
	/**
	 * 取消关注。
	 * @param context
	 * 上下文。
	 */
	void  removeUnsubscribe(Context context);
}