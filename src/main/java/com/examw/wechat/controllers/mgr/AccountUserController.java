package com.examw.wechat.controllers.mgr;

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
import com.examw.wechat.domain.mgr.AccountUser;
import com.examw.wechat.domain.security.Right;
import com.examw.wechat.model.mgr.AccountUserInfo;
import com.examw.wechat.service.mgr.IAccountUserService;

/**
 * 微信关注用户控制器。
 * @author yangyong.
 * @since 2014-06-17.
 */
@Controller
@RequestMapping(value = "/mgr/usr")
public class AccountUserController {
	private static Logger logger = Logger.getLogger(AccountUserController.class);
	//微信关注用户服务接口。
	@Resource
	private IAccountUserService accountUserService;
	/**
	 * 列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.MGR_USR + ":" + Right.VIEW})
	@RequestMapping(value = {"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("PER_UPDATE", ModuleConstant.MGR_USR + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.MGR_USR + ":" + Right.DELETE);
		
		model.addAttribute("USER_STATUS_SUBSCRIBE", this.accountUserService.loadStatusName(AccountUser.USER_STATUS_SUBSCRIBE));
		model.addAttribute("USER_STATUS_UNSUBSCRIBE", this.accountUserService.loadStatusName(AccountUser.USER_STATUS_UNSUBSCRIBE));
		 return "mgr/usr_list";
	}
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.MGR_USR + ":" + Right.VIEW})
	@RequestMapping(value = "/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<AccountUserInfo> datagrid(AccountUserInfo info){
		return this.accountUserService.datagrid(info);
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.MGR_USR + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		Json result = new Json();
		try {
			this.accountUserService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}