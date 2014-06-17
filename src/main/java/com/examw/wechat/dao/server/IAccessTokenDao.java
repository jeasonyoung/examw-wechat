package com.examw.wechat.dao.server;

import java.util.List;

import com.examw.wechat.dao.IBaseDao;
import com.examw.wechat.domain.server.AccessToken;
import com.examw.wechat.model.server.AccessTokenInfo;
/**
 * 公众号全局唯一票据数据接口。
 * @author yangyong.
 * @since 2014-04-03.
 * */
public interface IAccessTokenDao extends IBaseDao<AccessToken> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据集合。
	 * */
	List<AccessToken> findAccessTokens(AccessTokenInfo info);
	/**
	 * 查询数据总数。
	 * @param	info
	 * 	查询条件。
	 * @return
	 * 数据总数。
	 * */
	Long total(AccessTokenInfo info);
	/**
	 * 加载公众号全局唯一票据。
	 * @param accountId
	 * 公众号。
	 * @return
	 * 全局唯一票据。
	 * */
	AccessToken loadAccessToken(String accountId);
}