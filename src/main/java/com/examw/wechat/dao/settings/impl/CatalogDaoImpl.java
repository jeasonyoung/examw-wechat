package com.examw.wechat.dao.settings.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.wechat.dao.impl.BaseDaoImpl;
import com.examw.wechat.dao.settings.ICatalogDao;
import com.examw.wechat.domain.settings.Catalog;
import com.examw.wechat.model.settings.CatalogInfo;
/**
 * 考试类型数据接口实现。
 * @author yangyong.
 * @since 2014-04-28.
 */
public class CatalogDaoImpl extends BaseDaoImpl<Catalog> implements ICatalogDao {
	/*
	 * 查询数据。
	 * @see com.examw.wechat.dao.settings.ICatalogDao#findCatalogs(com.examw.wechat.model.settings.CatalogInfo)
	 */
	@Override
	public List<Catalog> findCatalogs(CatalogInfo info) {
		String hql = "from Catalog c where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by c." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.wechat.dao.settings.ICatalogDao#total(com.examw.wechat.model.settings.CatalogInfo)
	 */
	@Override
	public Long total(CatalogInfo info) {
		String hql = "select count(*) from Catalog c where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	/**
	 * 添加查询条件到HQL。
	 * @param info
	 * 查询条件。
	 * @param hql
	 * HQL
	 * @param parameters
	 * 参数。
	 * @return
	 * HQL
	 */
	protected String addWhere(CatalogInfo info,String hql,Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and ((c.name like :name) or (c.abbr_cn like :name) or (c.abbr_en like :name)) ";
			parameters.put("name", "%"+ info.getName() +"%");
		}
		return hql;
	}
}