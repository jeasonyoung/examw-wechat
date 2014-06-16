package com.examw.wechat.dao.security.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.wechat.dao.impl.BaseDaoImpl;
import com.examw.wechat.dao.security.IRightDao;
import com.examw.wechat.domain.security.Right;
import com.examw.wechat.model.security.RightInfo;

/**
 * 基本权限数据接口实现。
 * @author yangyong.
 * @since 2014-05-03.
 */
public class RightDaoImpl extends BaseDaoImpl<Right> implements IRightDao {
	/*
	 * 查询数据。
	 * @see examw.wechat.dao.security.IRightDao#findRights(examw.wechat.model.security.RightInfo)
	 */
	@Override
	public List<Right> findRights(RightInfo info) {
		String hql = "from Right r where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see examw.wechat.dao.security.IRightDao#total(examw.wechat.model.security.RightInfo)
	 */
	@Override
	public Long total(RightInfo info) {
		String hql = "select count(*) from Right r where 1=1 ";
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
	protected String addWhere(RightInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and (r.name like :name)";
			parameters.put("name", info.getName());
		}
		return hql;
	}
}