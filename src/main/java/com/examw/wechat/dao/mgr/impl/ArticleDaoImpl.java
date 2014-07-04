package com.examw.wechat.dao.mgr.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.wechat.dao.impl.BaseDaoImpl;
import com.examw.wechat.dao.mgr.IArticleDao;
import com.examw.wechat.domain.mgr.Article;
import com.examw.wechat.model.mgr.ArticleInfo;

/**
 * 资讯文档数据接口实现。
 * @author yangyong.
 * @since 2014-06-19.
 */
public class ArticleDaoImpl extends BaseDaoImpl<Article> implements IArticleDao {
	/*
	 * 查询数据。
	 * @see com.examw.wechat.dao.mgr.IArticleDao#findArticles(com.examw.wechat.model.mgr.ArticleInfo)
	 */
	@Override
	public List<Article> findArticles(ArticleInfo info) {
		String hql = "from Article a where (a.parent is null) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by a." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.wechat.dao.mgr.IArticleDao#total(com.examw.wechat.model.mgr.ArticleInfo)
	 */
	@Override
	public Long total(ArticleInfo info) {
		String hql = "select count(*) from Article a where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	/**
	 * 查询条件。
	 * @param info
	 * 查询条件。
	 * @param hql
	 * HQL。
	 * @param parameters
	 * 查询参数。
	 * @return HQL。
	 */
	protected String addWhere(ArticleInfo info, String hql,Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getProvinceId())){
			hql += " and (a.province.id = :provinceId) ";
			parameters.put("provinceId", info.getProvinceId());
		}
		if(!StringUtils.isEmpty(info.getCatalogId())){
			hql += " and (a.exam.catalog.id = :catalogId) ";
			parameters.put("catalogId", info.getCatalogId());
		}
		if(!StringUtils.isEmpty(info.getExamId())){
			hql += " and (a.exam.id = :examId)";
			parameters.put("examId", info.getExamId());
		}
		if(!StringUtils.isEmpty(info.getTitle())){
			hql += " and (a.title like :title)";
			parameters.put("title", "%"+info.getTitle() +"%");
		}
		if(!StringUtils.isEmpty(info.getType())){
			hql += " and (a.type = :type)";
			parameters.put("type", info.getType());
		}
		return hql;
	}
	/*
	 * 删除数据。
	 * @see com.examw.wechat.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(Article data){
		if(data == null)return;
		if(data.getChildren() != null && data.getChildren().size() > 0){
			for(Article c : data.getChildren()){
				if(c == null) continue;
				this.delete(c);
			}
		}
		super.delete(data);
	}
}