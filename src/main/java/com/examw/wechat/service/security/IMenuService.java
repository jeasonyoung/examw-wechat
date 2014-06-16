package com.examw.wechat.service.security;

import java.util.List;

import com.examw.configuration.ModuleDefineCollection;
import com.examw.configuration.ModuleSystem; 
import com.examw.wechat.model.security.MenuInfo;

/**
 * 菜单服务接口。
 * @author yangyong.
 * @since 2014-04-26.
 */
public interface IMenuService {
	/**
	 * 加载系统信息。
	 * @return
	 * 系统信息对象。
	 */
	ModuleSystem loadSystem();
	/**
	 * 加载系统名称。
	 * @return
	 * 系统名称。
	 */
	String loadSystemName();
	/**
	 * 加载系统模块集合。
	 * @return
	 * 系统模块集合。
	 */
	ModuleDefineCollection loadModules();
	/**
	 * 加载菜单数据。
	 * @return
	 * 菜单数据集合。
	 */
	List<MenuInfo> loadMenus();
	/**
	 * 更新菜单数据。
	 * @param info
	 * 源菜单。
	 * @return
	 * 更新后菜单数据。
	 */
	MenuInfo update(MenuInfo info);
	/**
	 * 删除数据。
	 * @param ids
	 * 菜单ID数组。
	 */
	void delete(String[] ids);
	/**
	 * 初始化。
	 * @throws Exception
	 */
	void init() throws Exception;
}