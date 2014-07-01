package com.examw.wechat.controllers.settings;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.model.TreeNode;
import com.examw.wechat.domain.security.Right;
import com.examw.wechat.model.settings.ExamInfo;
import com.examw.wechat.service.settings.IExamService;

/**
 * 考试控制器。
 * @author fengwei.
 * @since 2014-04-29.
 * 
 * @author yangyong
 * @since 2014-05-22.
 * 重构代码：
 * 1.优化结构；
 * 2.添加权限控制；
 */
@Controller
@RequestMapping(value = "/settings/exam")
public class ExamController {
	private static Logger logger  = Logger.getLogger(ExamController.class);
	//考试数据接口
	@Resource
	private IExamService examService;
	/**
	 * 考试列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_EXAM + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("PER_UPDATE", ModuleConstant.SETTINGS_EXAM + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SETTINGS_EXAM + ":" + Right.DELETE);
		return "settings/exam_list";
	}
	/**
	 * 考试编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_EXAM + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(String catalogId, Model model){
		model.addAttribute("CURRENT_CATALOG_ID", StringUtils.isEmpty(catalogId) ? "" : catalogId);
		return "settings/exam_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_EXAM + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<ExamInfo> datagrid(ExamInfo info){
		return this.examService.datagrid(info);
	}
	/**
	 * 类别下的所有考试树结构。
	 * @return
	 */
	@RequestMapping(value={"/tree"}, method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<TreeNode> tree(){
		return this.examService.loadCatalogExams();
	}
	/**
	 * 获取类型下的考试。
	 * @param catalogId
	 * @return
	 */
	@RequestMapping(value="/all", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<ExamInfo> all(String catalogId){
		final String catalog_id = StringUtils.isEmpty(catalogId) ? UUID.randomUUID().toString() : catalogId;
		return this.examService.datagrid(new ExamInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getPage() {return null;}
			@Override
			public Integer getRows() {return null;}
			@Override
			public String getCatalogId(){return catalog_id;}
			@Override
			public String getSort() {return "orderNo";}
			@Override
			public String getOrder() {return "asc";}
		}).getRows();
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_EXAM + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(ExamInfo info){
		Json result = new Json();
		try {
			result.setData(this.examService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新考试数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_EXAM + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		Json result = new Json();
		try {
			this.examService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}