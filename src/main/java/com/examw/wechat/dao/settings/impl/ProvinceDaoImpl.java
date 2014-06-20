package com.examw.wechat.dao.settings.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.wechat.dao.impl.BaseDaoImpl;
import com.examw.wechat.dao.settings.IProvinceDao;
import com.examw.wechat.domain.settings.Province;
import com.examw.wechat.model.settings.ProvinceInfo;

/**
 * 省份数据访问实现。
 * @author yangyong.
 * @since 2014-06-20.
 */
public class ProvinceDaoImpl extends BaseDaoImpl<Province> implements IProvinceDao {
	/*
	 * 查询数据。
	 * @see com.examw.wechat.dao.settings.IProvinceDao#findProvinces(com.examw.wechat.model.settings.ProvinceInfo)
	 */
	@Override
	public List<Province> findProvinces(ProvinceInfo info) {
		String hql = "from Province p where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by p." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.wechat.dao.settings.IProvinceDao#total(com.examw.wechat.model.settings.ProvinceInfo)
	 */
	@Override
	public Long total(ProvinceInfo info) {
		String hql = "select count(*) from Province p where 1 = 1 ";
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
	protected String addWhere(ProvinceInfo info,String hql,Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and (p.code like :name or p.name like :name)";
			parameters.put("name", "%" + info.getName()+ "%");
		}
		return hql;
	}
}