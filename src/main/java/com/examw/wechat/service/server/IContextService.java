package com.examw.wechat.service.server;

import com.examw.wechat.message.Context;

/**
 * 微信上下文服务。
 * @author yangyong.
 * @since 2014-04-10.
 * */
public interface IContextService {
	/**
	 *  获取微信用户上下文。
	 *  @param accountId
	 *   所属公众号Id。
	 *  @param  userOpenId
	 *   微信用户OpenId。
	 *  @return 微信用户上下文。
	 * */
	Context get(String accountId,String userOpenId);
	/**
	 * 更新上下文。
	 * @param context
	 *  上下文对象。
	 * */
	void update(Context context);
	/**
	 * 移除上下文。
	 * @param  context
	 *  上下文。
	 * */
	void remove(Context context);
}