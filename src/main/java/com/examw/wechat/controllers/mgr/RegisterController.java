package com.examw.wechat.controllers.mgr;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.wechat.model.mgr.RegisterInfo;
import com.examw.wechat.model.settings.CatalogInfo;
import com.examw.wechat.service.mgr.IRegisterService;
import com.examw.wechat.service.settings.IExamService;

/**
 * 登记用户控制器。
 * @author yangyong.
 * @since 2014-06-20.
 */
@Controller
@RequestMapping(value = "/mgr/register")
public class RegisterController {
	private static Logger logger = Logger.getLogger(RegisterController.class);
	/**
	 * 登记用户服务。
	 */
	@Resource
	private IRegisterService registerService;
	/**
	 * 考试服务。
	 */
	@Resource
	private IExamService examService;
	/**
	 * 列表页面。
	 * @return
	 */
	@RequestMapping(value = {"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		return "/mgr/register_list";
	}
	/**
	 * 编辑页面。
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(String catalogId,String examId, Model model){
		if(!StringUtils.isEmpty(examId)){
			CatalogInfo c = this.examService.loadCatalog(examId);
			if(c != null) catalogId = c.getId();
		}
		model.addAttribute("CURRENT_CATALOG_ID", StringUtils.isEmpty(catalogId) ? "" : catalogId);
		model.addAttribute("CURRENT_EXAM_ID", StringUtils.isEmpty(examId) ? "" : examId);
		return "/mgr/register_edit";
	}
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<RegisterInfo> datagrid(RegisterInfo info){
		return this.registerService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(RegisterInfo info, HttpServletRequest request){
		Json result = new Json();
		try {
			info.setIP(request.getRemoteAddr());
			result.setData(this.registerService.update(info));
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
			this.registerService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}