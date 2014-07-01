package com.examw.wechat.controllers.mgr;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.wechat.domain.mgr.Article;
import com.examw.wechat.domain.security.Right;
import com.examw.wechat.model.mgr.ArticleInfo;
import com.examw.wechat.service.mgr.IArticleService;

/**
 * 资讯文档控制器。
 * @author yangyong.
 * @since 2014-06-19.
 */
@Controller
@RequestMapping(value = "/mgr/article")
public class ArticleController {
	private static Logger logger = Logger.getLogger(ArticleController.class);
	//资讯文档服务。
	@Resource
	private IArticleService articleService;
	/**
	 * 列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.MGR_ARTICLE + ":" + Right.VIEW})
	@RequestMapping(value = {"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("PER_UPDATE", ModuleConstant.MGR_ARTICLE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.MGR_ARTICLE + ":" + Right.DELETE);
		
		model.addAttribute("ARTICLE_TYPE_TEXT_VALUE", Article.TYPE_TEXT);
		model.addAttribute("ARTICLE_TYPE_TEXT_NAME", this.articleService.loadTypeName(Article.TYPE_TEXT));
		
		model.addAttribute("ARTICLE_TYPE_NEWS_VALUE", Article.TYPE_NEWS);
		model.addAttribute("ARTICLE_TYPE_NEWS_NAME", this.articleService.loadTypeName(Article.TYPE_NEWS));
		
		model.addAttribute("ARTICLE_TYPE_ARTICLE_VALUE", Article.TYPE_ARTICLE);
		model.addAttribute("ARTICLE_TYPE_ARTICLE_NAME", this.articleService.loadTypeName(Article.TYPE_ARTICLE));
		return "/mgr/article_list";
	}
	/**
	 * 编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.MGR_ARTICLE + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit/{type}", method = RequestMethod.GET)
	public String edit(@PathVariable String type, String catalogId,String examId, Model model){
		model.addAttribute("CURRENT_CATALOG_ID", StringUtils.isEmpty(catalogId) ? "" : catalogId);
		model.addAttribute("CURRENT_EXAM_ID", StringUtils.isEmpty(examId) ? "" : examId);
		
		model.addAttribute("CURRENT_TYPE_VALUE", StringUtils.isEmpty(type) ? "": type);
		return "/mgr/article_edit_"+ type;
	}
	/**
	 * 获取资讯子图文选项。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.MGR_ARTICLE + ":" + Right.UPDATE})
	@RequestMapping(value = "/opt", method = RequestMethod.GET)
	public String options(String catalogId,String examId,Model model){
		model.addAttribute("CURRENT_ID", UUID.randomUUID().toString());
		
		model.addAttribute("CURRENT_CATALOG_ID", StringUtils.isEmpty(catalogId) ? "" : catalogId);
		model.addAttribute("CURRENT_EXAM_ID", StringUtils.isEmpty(examId) ? "" : examId);
		return "/mgr/article_opt";
	}
	/**
	 * 加载资讯集合。
	 * @param catalogId
	 * @param examId
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/articles", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<ArticleInfo> loadArticles(final String catalogId,final String examId,final Integer type){
		return this.articleService.datagrid(new ArticleInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getCatalogId() { return catalogId;}
			@Override
			public String getExamId() {return examId;}
			@Override
			public Integer getType() { return type;}
		}).getRows();
	}
	/**
	 * 加载资讯文档。
	 * @param id
	 * 资讯文档ID。
	 * @return
	 */
	@RequestMapping(value = "/articles/{id}", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ArticleInfo loadArticle(@PathVariable String id){
		if(StringUtils.isEmpty(id)) return null;
		return this.articleService.loadArticle(id);
	}
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.MGR_ARTICLE + ":" + Right.VIEW})
	@RequestMapping(value = "/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<ArticleInfo> datagrid(ArticleInfo info){
		return this.articleService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.MGR_ARTICLE + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody ArticleInfo info){
		Json result = new Json();
		try {
			result.setData(this.articleService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新资讯文档数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.MGR_ARTICLE + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		Json result = new Json();
		try {
			this.articleService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}