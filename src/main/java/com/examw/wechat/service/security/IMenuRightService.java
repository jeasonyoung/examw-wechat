package com.examw.wechat.service.security;

import com.examw.wechat.model.security.MenuRightInfo;
import com.examw.wechat.service.IBaseDataService;
 
/**
 * 菜单权限服务。
 * @author yangyong.
 * @since 2014-05-04.
 */
public interface IMenuRightService extends IBaseDataService<MenuRightInfo> {
	/**
	 * 菜单权限初始化。
	 * @throws Exception
	 */
	void init() throws Exception;
}