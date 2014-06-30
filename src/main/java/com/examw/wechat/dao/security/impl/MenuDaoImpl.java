package com.examw.wechat.dao.security.impl;
 
import java.util.HashMap;
import java.util.List; 
import java.util.Map;

import com.examw.wechat.dao.impl.BaseDaoImpl;
import com.examw.wechat.dao.security.IMenuDao;
import com.examw.wechat.domain.security.Menu;

/**
 * 菜单数据操作实现类。
 * @author yangyong.
 * @since 2014-04-28.
 */
public class MenuDaoImpl  extends BaseDaoImpl<Menu> implements IMenuDao{
	/*
	 * 查询数据。
	 * @see examw.wechat.dao.security.IMenuDao#findMenus()
	 */
	@Override
	public List<Menu> findMenus() {
		final String hql =  "from Menu m  where m.parent = null order by m.orderNo";
		return this.find(hql, null, null, null);
	}
	/*
	 * 加载子菜单集合。
	 * @see com.examw.wechat.dao.security.IMenuDao#loadChildren(java.lang.String)
	 */
	@Override
	public List<Menu> loadChildren(String pid) {
		final String hql =  "from Menu m  where m.parent.id = :pid order by m.orderNo";
		Map<String,Object> parameters = new HashMap<String, Object>();
		parameters.put("pid", pid);
		return this.find(hql, parameters, null, null); 
	}
	/*
	 * 删除菜单。
	 * @see com.examw.netplatform.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(Menu data){
		if(data == null) return;
		if(data.getChildren() != null){
			for(Menu m: data.getChildren()){
				if(m == null) continue;
				this.delete(m);
			}
		}
		super.delete(data);
	}
}