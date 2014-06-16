package com.examw.wechat.dao.security;

import java.util.List;

import com.examw.wechat.dao.IBaseDao;
import com.examw.wechat.domain.security.MenuRight;
import com.examw.wechat.model.security.MenuRightInfo;

/**
 * 菜单权限数据访问接口。
 * @author yangyong.
 * @since 2014-05-04.
 */
public interface IMenuRightDao extends IBaseDao<MenuRight> {
	/**
	 * 加载数据。
	 * @param info
	 * @return
	 */
	MenuRight load(MenuRightInfo info);
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	 List<MenuRight> findMenuRights(MenuRightInfo info);
	 /**
	  * 查询菜单ID下的所有权限。
	  * @param menuId
	  * 菜单ID
	  * @return
	  * 菜单权限集合。
	  */
	 List<MenuRight> findMenuRights(String menuId);
	 /**
		 * 查询数据总数。
		 * @param info
		 * 查询条件。
		 * @return
		 * 数据总数。
		 */
	 Long total(MenuRightInfo info);
}