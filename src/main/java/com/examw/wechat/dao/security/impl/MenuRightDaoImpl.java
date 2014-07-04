package com.examw.wechat.dao.security.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.wechat.dao.impl.BaseDaoImpl;
import com.examw.wechat.dao.security.IMenuRightDao;
import com.examw.wechat.domain.security.MenuRight;
import com.examw.wechat.model.security.MenuRightInfo;
/**
 * 菜单权限数据接口实现。
 * @author yangyong.
 * @since 2014-05-04.
 */
public class MenuRightDaoImpl extends BaseDaoImpl<MenuRight> implements IMenuRightDao {
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.IMenuRightDao#findMenuRights(com.examw.netplatform.model.admin.MenuRightInfo)
	 */
	@Override
	public List<MenuRight> findMenuRights(MenuRightInfo info) {
		String hql = "from MenuRight m where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(info.getSort().equalsIgnoreCase("menuName")){
				info.setSort("menu.name");
			}
			if(info.getSort().equalsIgnoreCase("rightName")){
				info.setSort("right.name");
			}
			hql += " order by m." + info.getSort() + " " + info.getOrder();
		}
		return  this.find(hql, parameters, info.getPage(), info.getRows());
	}
    /*
     * 查询数据总数。
     * @see com.examw.netplatform.dao.admin.IMenuRightDao#total(com.examw.netplatform.model.admin.MenuRightInfo)
     */
	@Override
	public Long total(MenuRightInfo info) {
		String hql = "select count(*) from MenuRight m where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//添加查询条件到HQL。
	private String addWhere(MenuRightInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getMenuId())){
			hql += " and (m.menu.id = :menuId or m.menu.parent.id = :menuId)";
			parameters.put("menuId", info.getMenuId());
		}
		if(!StringUtils.isEmpty(info.getMenuName())){
			hql += " and (m.menu.name like :menuName)";
			parameters.put("menuName", "%" + info.getMenuName() + "%");
		}
		if(!StringUtils.isEmpty(info.getRightName())){
			hql += " and (m.right.name like :rightName)";
			parameters.put("rightName", "%" + info.getRightName()+ "%");
		}
		return hql;
	}
	/*
	 * 加载数据。
	 * @see com.examw.netplatform.dao.admin.IMenuRightDao#load(com.examw.netplatform.model.admin.MenuInfo)
	 */
	@Override
	public MenuRight load(MenuRightInfo info) {
		if(info == null) return null;
		
		MenuRight data = StringUtils.isEmpty(info.getId()) ?  null : this.load(MenuRight.class, info.getId());
		if(data != null) return data;
		
		final String hql = "from MenuRight m where m.menu.id = :menuId and m.right.id = :rightId";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("menuId", info.getMenuId());
		parameters.put("rightId", info.getRightId());
		
		List<MenuRight> list = this.find(hql, parameters, null, null);
		if(list != null && list.size() > 0) return list.get(0);
		
		return null;
	}
	/*
	 * 查询菜单下的权限。
	 * @see com.examw.netplatform.dao.admin.IMenuRightDao#findMenuRights(java.lang.String)
	 */
	@Override
	public List<MenuRight> findMenuRights(String menuId) {
		final String hql = "from MenuRight m where m.menu.id = :menuId";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("menuId", menuId);
		return this.find(hql, parameters, null, null);
	}
}