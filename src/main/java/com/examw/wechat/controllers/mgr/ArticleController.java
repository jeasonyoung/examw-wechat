package com.examw.wechat.controllers.mgr;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
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
	@Resource
	private IArticleService articleService;
	/**
	 * 列表页面。
	 * @return
	 */
	@RequestMapping(value = {"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		return "/mgr/article_list";
	}
	/**
	 * 编辑页面。
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model){
		return "/mgr/article_edit";
	}
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
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