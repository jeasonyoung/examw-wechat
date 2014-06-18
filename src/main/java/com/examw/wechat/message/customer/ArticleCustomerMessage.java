package com.examw.wechat.message.customer;

import java.util.List;

import com.examw.wechat.message.Article;
/**
 * 发送图文客服消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class ArticleCustomerMessage extends BaseCustomerMessage {
	private static final long serialVersionUID = 1L;
	private List<Article> articles;
	/**
	 * 获取图文信息集合。
	 * @return 图文信息集合。
	 * */
	public List<Article> getArticles() {
		return articles;
	}
	/**
	 * 设置图文信息集合。
	 * @param articles
	 * 	图文信息集合。
	 * */
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
}