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
import com.examw.wechat.model.security.LoginLogInfo;
import com.examw.wechat.service.security.ILoginLogService;
import com.examw.wechat.domain.security.Right;
/**
 * 登录日志管理控制器。
 * @author yangyong.
 * @since 2014-05-19.
 */
@Controller
@RequestMapping(value = "/security/log")
public class LogController {
	private static final Logger logger = Logger.getLogger(LogController.class);
	//登录日志服务接口。
	@Resource
	private ILoginLogService loginLogService;
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_LOGIN_LOG + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_DELETE", ModuleConstant.SECURITY_LOGIN_LOG + ":" + Right.DELETE);
		return "security/log_list";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_LOGIN_LOG + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<LoginLogInfo> datagrid(LoginLogInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.loginLogService.datagrid(info);
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_LOGIN_LOG + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据［"+ id +"］...");
		Json result = new Json();
		try {
			this.loginLogService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}