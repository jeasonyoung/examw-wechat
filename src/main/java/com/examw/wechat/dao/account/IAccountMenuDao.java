package com.examw.wechat.dao.account;

import java.util.List;

import com.examw.wechat.dao.IBaseDao;
import com.examw.wechat.domain.account.AccountMenu;

/**
 * 微信公众号菜单数据访问接口。
 * @author yangyong.
 * @since 2014-04-02.
 * */
public interface IAccountMenuDao extends IBaseDao<AccountMenu> {
	/**
	 * 查找公众号下的一级菜单集合。
	 * @param accountId
	 * 	公众号ID。
	 * @return
	 * 菜单集合。
	 * */
	List<AccountMenu> findMenus(String accountId);
	/**
	 * 加载菜单对象。
	 * @param accountId
	 *  公众号ID。
	 * @param code
	 * 菜单代码。
	 * @return
	 * 菜单对象。
	 * */
	AccountMenu loadMenu(String accountId,String code);
}