package com.examw.wechat.dao.settings.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.wechat.dao.impl.BaseDaoImpl;
import com.examw.wechat.dao.settings.ISubjectDao;
import com.examw.wechat.domain.settings.Subject;
import com.examw.wechat.model.settings.SubjectInfo;

/**
 * 科目数据接口实现类
 * @author fengwei.
 * @since 2014年4月29日 上午11:52:45.
 */
public class SubjectDaoImpl extends BaseDaoImpl<Subject>implements ISubjectDao {
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.dao.admin.settings.ISubjectDao#findSubjects(com.examw.netplatform.model.admin.settings.SubjectInfo)
	 */
	@Override
	public List<Subject> findSubjects(SubjectInfo info) {
		String hql = "from Subject s where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(info.getSort().equalsIgnoreCase("examName")){
				info.setSort("exam.name");
			}else if(info.getSort().equalsIgnoreCase("catalogName")){
				info.setSort("exam.catalog.name");
			}
			hql += " order by s." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据汇总。
	 * @see com.examw.netplatform.dao.admin.settings.ISubjectDao#total(com.examw.netplatform.model.admin.settings.SubjectInfo)
	 */
	@Override
	public Long total(SubjectInfo info) {
		String hql = "select count(*) from Subject s where 1 = 1 ";
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
	protected String addWhere(SubjectInfo info,String hql,Map<String, Object> parameters){
		//科目名称查询
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and (s.name like :name)";
			parameters.put("name", "%"+ info.getName() +"%");
		}
		//考试类别
		if(!StringUtils.isEmpty(info.getCatalogId())){
			hql += " and (s.exam.id = :catalogId)";
			parameters.put("catalogId", info.getCatalogId());
		}
		//考试ID
		if(!StringUtils.isEmpty(info.getExamId()))
		{
			hql += " and (s.exam.id = :examId)";
			parameters.put("examId", info.getExamId());
		}
		//考试名称查询
		if(!StringUtils.isEmpty(info.getExamName()))
		{
			hql += "and ((s.exam.name like :examname) or (s.exam.abbr_cn like :examname))";
			parameters.put("examname", "%"+ info.getExamName() +"%");
		}
		return hql;
	}
}