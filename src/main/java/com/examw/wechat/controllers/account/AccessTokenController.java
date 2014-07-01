package com.examw.wechat.controllers.account;

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
import com.examw.wechat.model.account.AccessTokenInfo;
import com.examw.wechat.service.account.IAccessTokenService;

/**
 * 公众号票据控制器。
 * @author yangyong.
 * @since 2014-05-17.
 */
@Controller
@RequestMapping(value = "/accounts/token")
public class AccessTokenController {
	private static Logger logger = Logger.getLogger(AccessTokenController.class);
	//票据服务接口。 
	@Resource
	private IAccessTokenService accessTokenService;
	/**
	 * 列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.ACCOUNTS_TOKEN + ":" + Right.VIEW})
	@RequestMapping(value = {"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("PER_DELETE", ModuleConstant.ACCOUNTS_MENU + ":" + Right.DELETE);
		return "accounts/token_list";
	}
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.ACCOUNTS_TOKEN + ":" + Right.VIEW})
	@RequestMapping(value = "/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<AccessTokenInfo> datagrid(AccessTokenInfo info){
		return this.accessTokenService.datagrid(info);
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.ACCOUNTS_TOKEN + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		Json result = new Json();
		try {
			this.accessTokenService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}