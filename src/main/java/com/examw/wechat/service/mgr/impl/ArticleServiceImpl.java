package com.examw.wechat.service.mgr.impl;

import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.wechat.dao.mgr.IArticleDao;
import com.examw.wechat.dao.settings.ISubjectDao;
import com.examw.wechat.domain.mgr.Article;
import com.examw.wechat.domain.settings.Subject;
import com.examw.wechat.model.mgr.ArticleInfo;
import com.examw.wechat.service.impl.BaseDataServiceImpl;
import com.examw.wechat.service.mgr.IArticleService;

/**
 * 资讯文档服务实现。
 * @author yangyong.
 * @since 2014-06-19.
 */
public class ArticleServiceImpl extends BaseDataServiceImpl<Article,ArticleInfo> implements IArticleService {
	private IArticleDao articleDao;
	private ISubjectDao subjectDao;
	/**
	 * 设置资讯文档数据接口。
	 * @param articleDao
	 * 资讯文档数据接口。
	 */
	public void setArticleDao(IArticleDao articleDao) {
		this.articleDao = articleDao;
	}
	/**
	 * 设置所属科目数据接口。
	 * @param subjectDao
	 * 所属科目数据接口。
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Article> find(ArticleInfo info) {
		return this.articleDao.findArticles(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected ArticleInfo changeModel(Article data) {
		ArticleInfo info = new ArticleInfo();
		BeanUtils.copyProperties(data, info, new String[]{"children"});
		if(data.getSubject() != null){
			info.setSubjectId(data.getSubject().getId());
			info.setSubjectName(data.getSubject().getName());
		}
		if(data.getChildren() != null && data.getChildren().size() > 0){
			Set<ArticleInfo> articles = new TreeSet<>(new Comparator<ArticleInfo>(){
				@Override
				public int compare(ArticleInfo o1, ArticleInfo o2) {
					return o1.getOrderNo() - o2.getOrderNo();
				}
			});
			for(Article child : data.getChildren()){
				if(child == null) continue;
				ArticleInfo e = this.changeModel(child);
				if(e != null) articles.add(e);
			}
			if(articles.size() > 0) info.setChildren(articles);
		}
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(ArticleInfo info) {
		return this.articleDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public ArticleInfo update(ArticleInfo info) {
		if(info == null) return null;
		boolean isAdded = false;
		Article data = StringUtils.isEmpty(info.getId()) ? null : this.articleDao.load(Article.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Article();
			data.setCreateTime(new Date());
		}
		if(!isAdded){
			while(data.getParent() != null){
				data = data.getParent();
			}
			if(data.getChildren() != null && data.getChildren().size() > 0){
				data.getChildren().clear();
			}
		}
		this.copyArticleProperties(info, data);
		if(data.getSubject() != null){
			info.setSubjectName(data.getSubject().getName());
		}
		if(isAdded)this.articleDao.save(data);
		return info;
	}
	/**
	 * 复制资讯文档属性。
	 * @param source
	 * @param target
	 */
	private void copyArticleProperties(ArticleInfo source, Article target){
		if(source == null || target == null) return;
		BeanUtils.copyProperties(source, target, new String[]{"children", "createTime"});
		if(!StringUtils.isEmpty(source.getSubjectId()) && (target.getSubject() == null || !target.getSubject().getId().equalsIgnoreCase(source.getSubjectId()))){
			Subject subject = this.subjectDao.load(Subject.class, source.getSubjectId());
			if(subject != null) target.setSubject(subject);
		}
		if(source.getChildren() != null && source.getChildren().size() > 0){
			Set<Article> children = new HashSet<>();
			for(ArticleInfo info :  source.getChildren()){
				if(info == null) continue;
				Article article = StringUtils.isEmpty(info.getId()) ? null : this.articleDao.load(Article.class, info.getId());
				if(article == null){
					if(StringUtils.isEmpty(info.getId())){
						info.setId(UUID.randomUUID().toString());
					}
					article = new Article();
					article.setCreateTime(new Date());
				}
				this.copyArticleProperties(info, article);
				article.setParent(target);
				children.add(article);
			}
			if(children.size() > 0) target.setChildren(children);
		}
	}
	/*
	 * 删除数据。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			Article data = this.articleDao.load(Article.class, ids[i]);
			if(data != null)this.articleDao.delete(data);
		}
	}
}