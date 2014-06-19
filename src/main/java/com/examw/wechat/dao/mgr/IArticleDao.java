package com.examw.wechat.dao.mgr;

import java.util.List;

import com.examw.wechat.dao.IBaseDao;
import com.examw.wechat.domain.mgr.Article;
import com.examw.wechat.model.mgr.ArticleInfo;

/**
 * 资讯文档数据接口。
 * @author yangyong.
 * @since 2014-06-19.
 */
public interface IArticleDao extends IBaseDao<Article> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据集合。
	 */
	List<Article> findArticles(ArticleInfo info);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 */
	Long total(ArticleInfo info);
}