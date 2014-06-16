package com.examw.wechat.service.security.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.util.StringUtils;

import com.examw.wechat.dao.security.IMenuDao;
import com.examw.wechat.dao.security.IMenuRightDao;
import com.examw.wechat.dao.security.IRightDao;
import com.examw.wechat.domain.security.Menu;
import com.examw.wechat.domain.security.MenuRight;
import com.examw.wechat.domain.security.Right;
import com.examw.wechat.model.security.MenuRightInfo;
import com.examw.wechat.service.impl.BaseDataServiceImpl;
import com.examw.wechat.service.security.IMenuRightService;

/**
 * 菜单权限服务接口。
 * @author yangyong.
 * @since 2014-05-04.
 */
public class MenuRightServiceImpl extends BaseDataServiceImpl<MenuRight, MenuRightInfo> implements IMenuRightService {
	private IMenuRightDao menuRightDao;
	private IMenuDao menuDao;
	private IRightDao rightDao;
	/**
	 * 设置菜单权限数据接口。
	 * @param menuRightDao
	 * 菜单权限数据接口。
	 */
	public void setMenuRightDao(IMenuRightDao menuRightDao) {
		this.menuRightDao = menuRightDao;
	}
	/**
	 * 设置菜单数据接口。
	 * @param menuDao
	 * 菜单数据接口。
	 */
	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}
	/**
	 * 设置权限数据接口。
	 * @param rightDao
	 * 权限数据接口。
	 */
	public void setRightDao(IRightDao rightDao) {
		this.rightDao = rightDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<MenuRight> find(MenuRightInfo info) {
		return this.menuRightDao.findMenuRights(info);
	}
    /*
     * 数据类型转换。
     * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
     */
	@Override
	protected MenuRightInfo changeModel(MenuRight data) {
		if(data == null) return null;
		MenuRightInfo info = new MenuRightInfo();
		info.setId(data.getId());
		info.setCode(data.getCode());
		info.setMenuId(data.getMenu().getId());
		info.setMenuName(data.getMenu().getName());
		info.setRightId(data.getId());
		info.setRightName(data.getRight().getName());
		return info;
	}
    /*
     * 查询数据总数。
     * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
     */
	@Override
	protected Long total(MenuRightInfo info) {
		return this.menuRightDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public MenuRightInfo update(MenuRightInfo info) {
		if(info == null) return null;
		if(StringUtils.isEmpty(info.getRightId())){
			throw new RuntimeException("必须选择权限！");
		}
		boolean isAdded = false;
		MenuRight data =  this.menuRightDao.load(info);
		if(isAdded = (data == null)){
			info.setId(UUID.randomUUID().toString());
			data = new MenuRight();
			data.setId(info.getId());
		}
		if(!StringUtils.isEmpty(info.getMenuId()) && (data.getMenu() == null || !data.getMenu().getId().equalsIgnoreCase(info.getMenuId()))){
			Menu menu = this.menuDao.load(Menu.class, info.getMenuId());
			if(menu != null) {
				if(menu.getChildren() != null && menu.getChildren().size() > 0){
					throw new RuntimeException("必须为叶子菜单才能赋予权限！");
				}
				data.setMenu(menu);
				info.setMenuName(menu.getName());
			}
		}
		
		if(!StringUtils.isEmpty(info.getRightId()) && (data.getRight() == null || !data.getRight().getId().equalsIgnoreCase(info.getRightId()))){
			Right right = this.rightDao.load(Right.class, info.getRightId());
			if(right != null){
				data.setRight(right);
				info.setRightName(right.getName());
			}
		}
		
		if(data.getMenu() != null && data.getRight() != null){
			info.setCode(data.getMenu().getId() + ":" + data.getRight().getValue());
			data.setCode(info.getCode());
		}
		
		if(StringUtils.isEmpty(info.getMenuName()) && data.getMenu() != null){
			info.setMenuName(data.getMenu().getName());
		}
		
		if(StringUtils.isEmpty(info.getRightName()) && data.getRight() != null){
			info.setRightName(data.getRight().getName());
		}
		
		if(isAdded)this.menuRightDao.save(data);
		
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			MenuRight data = this.menuRightDao.load(MenuRight.class, ids[i]);
			if(data != null) this.menuRightDao.delete(data);
		}
	}
}