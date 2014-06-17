package com.examw.wechat.controllers.settings;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.model.TreeNode;
import com.examw.wechat.model.settings.CatalogInfo;
import com.examw.wechat.service.settings.ICatalogService;

/**
 * 考试类别控制器。
 * @author yangyong.
 * @since 2014-04-28.
 */
@Controller
@RequestMapping(value = "/settings/catalog")
public class CatalogController {
	private static Logger logger  = Logger.getLogger(CatalogController.class);
	/**
	 * 考试类别数据接口
	 */
	@Resource
	private ICatalogService catalogService;
	/**
	 * 考试类别列表页面。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SETTINGS_CATALOG + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		//model.addAttribute("PER_UPDATE", ModuleConstant.SETTINGS_CATALOG + ":" + Right.UPDATE);
		//model.addAttribute("PER_DELETE", ModuleConstant.SETTINGS_CATALOG + ":" + Right.DELETE);
		return "settings/catalog_list";
	}
	/**
	 * 考试类别编辑页面。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SETTINGS_CATALOG + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(){
		return "settings/catalog_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SETTINGS_CATALOG + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<CatalogInfo> datagrid(CatalogInfo info){
		return this.catalogService.datagrid(info);
	}
	/**
	 * 考试类别全部数据。
	 * @return
	 */
	@RequestMapping(value="/all", method = RequestMethod.POST)
	@ResponseBody
	public List<CatalogInfo> all(){
		return this.catalogService.datagrid(new CatalogInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getPage() {return null;}
			@Override
			public Integer getRows() {return null;}
			@Override
			public String getOrder() {return "asc";}
			@Override
			public String getSort() {return "orderNo";};
		}).getRows();
	}
	/**
	 * 考试类别树结构数据。
	 * @return
	 */
	@RequestMapping(value = "/tree", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<TreeNode> tree(){
		List<TreeNode> result = new ArrayList<>();
		List<CatalogInfo> list = this.all();
		if(list != null && list.size() > 0){
			for(final CatalogInfo info : list){
				if(info == null) continue;
				result.add(new TreeNode(){
					private static final long serialVersionUID = 1L;
					@Override
					public String getId(){return info.getId();}
					@Override
					public String getText(){return info.getName();}
				});
			}
		}
		return result;
	}
	/**
	 * 考试类别考试树。
	 * @return
	 */
	@RequestMapping(value = "/exams-tree", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<TreeNode> allCatalogExams(){
		return this.catalogService.loadAllCatalogExams();
	}
	/**
	 * 考试科目树。
	 * @return
	 */
	@RequestMapping(value = "/subject-tree", method = { RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<TreeNode> allCatalogExamSubjects(){
		return this.catalogService.loadAllCatalogExamSubjects();
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	//@RequiresPermissions({ModuleConstant.SETTINGS_CATALOG + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(CatalogInfo info){
		Json result = new Json();
		try {
			result.setData(this.catalogService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新考试类型数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SETTINGS_CATALOG + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		Json result = new Json();
		try {
			this.catalogService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}