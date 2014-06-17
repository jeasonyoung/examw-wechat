package com.examw.wechat.service.account;

import com.examw.wechat.model.account.AccessTokenInfo;
import com.examw.wechat.service.IBaseDataService;

/**
 * 公众号全局唯一票据服务接口。
 * @author yangyong.
 * @since 2014-04-03.
 * */
public interface IAccessTokenService extends IBaseDataService<AccessTokenInfo> {
	/**
	 * 获取公众号全局唯一票据。
	 * @param accountId
	 * 公众号ID。
	 * @return
	 * 全局唯一票据。
	 * */
	String loadAccessToken(String accountId) throws Exception;
	/**
	 * 刷新公众号全局唯一票据。
	 * @param accountId
	 * 公众号全局唯一票据。
	 * */
	String refreshAccessToken(String accountId) throws Exception;
}