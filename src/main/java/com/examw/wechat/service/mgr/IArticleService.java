package com.examw.wechat.service.mgr;
import com.examw.wechat.model.mgr.ArticleInfo;
import com.examw.wechat.service.IBaseDataService;

/**
 * 资讯文档服务接口。
 * @author yangyong.
 * @since 2014-06-19.
 */
public interface IArticleService extends IBaseDataService<ArticleInfo> {
	/**
	 * 加载文档类型名称。
	 * @param type
	 * 资讯类型。
	 * @return
	 * 文档类型名称。
	 */
	String loadTypeName(Integer type);
	/**
	 * 加载资讯文档。
	 * @param articleId
	 * 资讯文档ID。
	 * @return
	 */
	ArticleInfo loadArticle(String articleId);
}