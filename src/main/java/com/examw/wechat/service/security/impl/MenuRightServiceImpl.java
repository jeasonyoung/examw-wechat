package com.examw.wechat.service.security.impl;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.wechat.dao.security.IMenuDao;
import com.examw.wechat.dao.security.IMenuRightDao;
import com.examw.wechat.dao.security.IRightDao;
import com.examw.wechat.domain.security.Menu;
import com.examw.wechat.domain.security.MenuRight;
import com.examw.wechat.domain.security.Right;
import com.examw.wechat.model.security.MenuRightInfo;
import com.examw.wechat.model.security.RightInfo;
import com.examw.wechat.service.impl.BaseDataServiceImpl;
import com.examw.wechat.service.security.IMenuRightService;

/**
 * 菜单权限服务接口。
 * @author yangyong.
 * @since 2014-05-04.
 */
public class MenuRightServiceImpl extends BaseDataServiceImpl<MenuRight, MenuRightInfo> implements IMenuRightService {
	private static Logger logger = Logger.getLogger(MenuRightServiceImpl.class);
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
		if(logger.isDebugEnabled())logger.debug("更新数据...");
		if(info == null) return null;
		String err =  null;
		if(StringUtils.isEmpty(info.getRightId())){
			logger.error(err = "必须选择权限！");
			throw new RuntimeException(err);
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
					logger.error(err = "必须为叶子菜单才能赋予权限！");
					throw new RuntimeException(err);
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
		if(isAdded)this.menuRightDao.save(data);
		if(StringUtils.isEmpty(info.getMenuName()) && data.getMenu() != null){
			info.setMenuName(data.getMenu().getName());
		}
		if(StringUtils.isEmpty(info.getRightName()) && data.getRight() != null){
			info.setRightName(data.getRight().getName());
		}
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled())logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			MenuRight data = this.menuRightDao.load(MenuRight.class, ids[i]);
			if(data != null) {
				if(logger.isDebugEnabled()) logger.debug("删除数据：" + ids[i]);
				this.menuRightDao.delete(data);
			}
		}
	}
	/*
	 * 初始化。
	 * @see com.examw.wechat.service.security.IMenuRightService#init()
	 */
	@Override
	public void init() throws Exception {
		if(logger.isDebugEnabled()) logger.debug("初始化菜单权限...");
		String err = null;
		 List<Right> rights = this.rightDao.findRights(new RightInfo(){private static final long serialVersionUID = 1L;});
		 if(rights == null || rights.size() == 0){
			 err = "没有查询到权限数据！";
			 if(logger.isDebugEnabled())logger.debug(err);
			 throw new Exception(err);
		 }
		 List<Menu> menus = this.menuDao.findMenus();
		 if(menus == null || menus.size() == 0) {
			 err = "没有查询到菜单数据！";
			 if(logger.isDebugEnabled()) logger.debug(err);
			 throw new Exception(err);
		 }
		 for(int i = 0; i < menus.size(); i++){
			 this.createMenuRights(menus.get(i), rights);
		 }
	}
	/**
	 * 创建菜单权限。
	 * @param menu
	 * @param rights
	 */
	private void createMenuRights(final Menu data, List<Right> rights){
		if(data == null) return;
		List<Menu> children = this.menuDao.loadChildren(data.getId());
		if(children != null && children.size() > 0){
			//查找叶子节点。
			for(Menu m : children){
				this.createMenuRights(m, rights);
			}
		} else {
			//添加权限。
			for(final Right right : rights){
				if(right == null) continue;
				boolean isAdded = false;
				MenuRight menuRight = this.menuRightDao.load(new MenuRightInfo(){
					private static final long serialVersionUID = 1L;
					//
					public String getMenuId(){ return data.getId();}
					//
					public String getRightId(){return right.getId();}
				});
				if(isAdded = (menuRight == null)){
					menuRight = new MenuRight();
					menuRight.setId(UUID.randomUUID().toString());
				}
				menuRight.setCode(data.getId() + ":" + right.getValue());
				menuRight.setMenu(data);
				menuRight.setRight(right);
				if(isAdded) this.menuRightDao.save(menuRight);
				if(logger.isDebugEnabled()){
					logger.debug("添加菜单["+data.getName()+","+data.getId()+"]权限["+ right.getName() +","+ right.getId()+"]...");
				}
			}
		}
	}
}