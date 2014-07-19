package com.examw.wechat.controllers.security;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.wechat.domain.security.Right;
import com.examw.wechat.model.security.RightInfo;
import com.examw.wechat.service.security.IRightService;
/**
 * 权限控制器。
 * @author yangyong.
 * @since 2014-05-03.
 */
@Controller
@RequestMapping(value = "/security/right")
public class RightController {
	private static final Logger logger = Logger.getLogger(RightController.class);
	//设置权限服务接口。
	@Resource
	private IRightService rightService;
	/**
	 * 加载列表页面。
	 * @return
	 * 列表页面。
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_RIGHT + ":" + Right.VIEW})
	@RequestMapping(value = {"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SECURITY_RIGHT + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SECURITY_RIGHT + ":" + Right.DELETE);
		return "security/right_list";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_RIGHT + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<RightInfo> datagrid(RightInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.rightService.datagrid(info);
	}
	/**
	 * 初始化数据。
	 * @return
	 * 初始化结果。
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_RIGHT + ":" + Right.UPDATE})
	@RequestMapping(value="/init", method = RequestMethod.POST)
	@ResponseBody
	public Json init(){
		if(logger.isDebugEnabled()) logger.debug("初始化数据...");
		Json result = new Json();
		try {
			this.rightService.init();
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("初始化基础权限数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_RIGHT + ":" + Right.UPDATE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据［"+ id +"］...");
		Json result = new Json();
		try {
			this.rightService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}