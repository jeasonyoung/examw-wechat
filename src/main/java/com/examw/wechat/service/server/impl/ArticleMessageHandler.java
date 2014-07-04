package com.examw.wechat.service.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.wechat.dao.mgr.IArticleDao;
import com.examw.wechat.dao.mgr.IRegisterDao;
import com.examw.wechat.domain.mgr.Article;
import com.examw.wechat.domain.mgr.Register;
import com.examw.wechat.message.Context;
import com.examw.wechat.message.events.ClickEventMessage;
import com.examw.wechat.message.resp.ArticleRespMessage;
import com.examw.wechat.message.resp.BaseRespMessage; 
import com.examw.wechat.message.resp.TextRespMessage;
import com.examw.wechat.model.mgr.ArticleInfo;

/**
 * 图文消息处理。
 * @author yangyong.
 * @since 2014-07-03.
 */
public class ArticleMessageHandler extends DefaultMessageHandler {
	private static Logger logger = Logger.getLogger(ArticleMessageHandler.class);
	private IRegisterDao registerDao;
	private IArticleDao articleDao;
	private String baseUrl,noArticleMsg;
	/**
	 * 设置用户登记数据接口。
	 * @param registerDao
	 */
	public void setRegisterDao(IRegisterDao registerDao) {
		this.registerDao = registerDao;
	}
	/**
	 * 设置图文数据接口。
	 * @param articleDao
	 */
	public void setArticleDao(IArticleDao articleDao) {
		this.articleDao = articleDao;
	}
	/**
	 * 设置原文链接配置。
	 * @param baseUrl
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	/**
	 * 设置未找到图文提示消息。
	 * @param noArticleMsg
	 */
	public void setNoArticleMsg(String noArticleMsg) {
		this.noArticleMsg = noArticleMsg;
	}
	/*
	 * 点击事件处理。
	 * @see com.examw.wechat.service.server.impl.DefaultMessageHandler#runClickReq(com.examw.wechat.message.Context, java.lang.String, com.examw.wechat.message.events.ClickEventMessage)
	 */
	@Override
	protected BaseRespMessage runClickReq(Context context, String menuKey,ClickEventMessage req){
		if(logger.isDebugEnabled())logger.debug("开始图文消息创建...");
		final Register register = this.registerDao.load(Register.class, context.getUserId());
		if(register == null){
			if(logger.isDebugEnabled())logger.debug("未找到当前用户［"+ context.getUserId()+"］的登记注册信息！");
			return null;
		}
		ArticleInfo info = new ArticleInfo();
		info.setProvinceId(register.getProvince() == null ? ""  : register.getProvince().getId());
		info.setExamId(register.getExam() == null ? "" : register.getExam().getId());
		info.setPage(1);
		info.setRows(2);
		info.setSort("createTime");
		info.setOrder("desc");
		List<Article> list = this.articleDao.findArticles(info);
		if(list == null || list.size() == 0){
			TextRespMessage resp = new TextRespMessage(req);
			resp.setContent(String.format(this.noArticleMsg, register.getProvince() == null ? "" : register.getProvince().getName(),
																register.getExam() == null ? "" : register.getExam().getName()));
			if(logger.isDebugEnabled()){
				logger.debug("未找到图文数据［ProvinceId="+info.getProvinceId()+",ExamId="+info.getExamId()+"］！");
				logger.debug(resp.getContent());
			}
			return resp;
		}
		return this.createArticleRespMessage(list.get(0), req);
	}
	//创建图文消息。
	private ArticleRespMessage createArticleRespMessage(Article data,ClickEventMessage req){
		List<com.examw.wechat.message.Article> articles = new ArrayList<>();
		articles.add(this.buildArticle(data));
		if(data.getChildren() != null){
			int index = 0;
			for(Article article : data.getChildren()){
				if(article == null) continue;
				if(index > ArticleRespMessage.MAX_COUNT - 2) break;
				articles.add(this.buildArticle(article));
				index+=1;
			}
		}
		ArticleRespMessage resp = new ArticleRespMessage(req);
		resp.setArticleCount(articles.size());
		resp.setArticles(articles);
		return resp;
	}
	//创建内容。
	private com.examw.wechat.message.Article buildArticle(Article data){
		if(logger.isDebugEnabled())logger.debug("创建［"+data.getTitle()+"］图文内容...");
		com.examw.wechat.message.Article article = new com.examw.wechat.message.Article();
		article.setTitle(data.getTitle());
		article.setPicUrl(data.getPicUrl());
		article.setDescription(data.getDescription());
		if(!StringUtils.isEmpty(data.getUrl())){
			if(StringUtils.isEmpty(this.baseUrl)){
				if(logger.isDebugEnabled()) logger.debug("未配置原文链接配置baseUrl");
				return article;
			}
			article.setUrl(String.format(this.baseUrl, data.getUrl()));
		}
		return article;
	}
}