package com.examw.wechat.dao.settings.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.wechat.dao.impl.BaseDaoImpl;
import com.examw.wechat.dao.settings.IExamDao;
import com.examw.wechat.domain.settings.Exam;
import com.examw.wechat.model.settings.ExamInfo;
/**
 * 考试数据接口实现类
 * 
 * @author fengwei.
 * @since 2014年4月29日 上午11:32:44.
 * 
 * @author yangyong
 * @since 2014-05-22
 * 重构代码。
 */
public class ExamDaoImpl extends BaseDaoImpl<Exam> implements IExamDao {
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.settings.IExamDao#findExams(com.examw.netplatform.model.admin.settings.ExamInfo)
	 */
	@Override
	public List<Exam> findExams(ExamInfo info) {
		String hql = "from Exam e where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if (!StringUtils.isEmpty(info.getSort())) {
			if(info.getSort().equalsIgnoreCase("catalogName")){
				info.setSort("catalog.name");
			}
			hql += " order by e." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询汇总。
	 * @see com.examw.netplatform.dao.admin.settings.IExamDao#total(com.examw.netplatform.model.admin.settings.ExamInfo)
	 */
	@Override
	public Long total(ExamInfo info) {
		String hql = "select count(*) from Exam e where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	/**
	 * 添加查询条件到HQL。
	 * 
	 * @param info
	 *            查询条件。
	 * @param hql
	 *            HQL
	 * @param parameters
	 *            参数。
	 * @return HQL
	 */
	protected String addWhere(ExamInfo info, String hql,Map<String, Object> parameters) {
		//考试名称、简称。
		if (!StringUtils.isEmpty(info.getName())) {
			hql += " and ((e.name like :name) or (e.abbr_cn like :name) or (e.abbr_en like :name))";
			parameters.put("name", "%" + info.getName() + "%");
		}
		//所属考试类别。
		if(!StringUtils.isEmpty(info.getCatalogId())){
			hql += " and (e.catalog.id = :catalogId)";
			parameters.put("catalogId", info.getCatalogId());
		}
		return hql;
	}
}