package com.examw.wechat.controllers.account;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.wechat.domain.account.Account;
import com.examw.wechat.model.account.AccountInfo;
import com.examw.wechat.service.account.IAccountService;

/**
 * 公众号管理控制器。
 * @author yangyong.
 * @since 2014-05-16.
 */
@Controller
@RequestMapping(value = "/accounts/account")
public class AccountController {
	private static Logger logger = Logger.getLogger(AccountController.class);
	/**
	 * 公众号管理服务。
	 */
	@Resource
	private IAccountService accountService;
	/**
	 * 列表页面。
	 * @return
	 */
	@RequestMapping(value = {"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("STATUS_DISABLE", this.accountService.loadStatusName(Account.ACCOUNT_STATUS_DISABLE));
		model.addAttribute("STATUS_ENABLE", this.accountService.loadStatusName(Account.ACCOUNT_STATUS_ENABLE));
		
		model.addAttribute("TYPE_SERVICE", this.accountService.loadTypeName(Account.ACCOUNT_TYPE_SERVICE));
		model.addAttribute("TYPE_SUBSCRIBE", this.accountService.loadTypeName(Account.ACCOUNT_TYPE_SUBSCRIBE));
		return "accounts/account_list";
	}
	/**
	 * 编辑页面。
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model){
		model.addAttribute("STATUS_DISABLE", this.accountService.loadStatusName(Account.ACCOUNT_STATUS_DISABLE));
		model.addAttribute("STATUS_ENABLE", this.accountService.loadStatusName(Account.ACCOUNT_STATUS_ENABLE));
		
		model.addAttribute("TYPE_SERVICE", this.accountService.loadTypeName(Account.ACCOUNT_TYPE_SERVICE));
		model.addAttribute("TYPE_SUBSCRIBE", this.accountService.loadTypeName(Account.ACCOUNT_TYPE_SUBSCRIBE));
		return "accounts/account_edit";
	}
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<AccountInfo> datagrid(AccountInfo info){
		return this.accountService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(AccountInfo info){
		Json result = new Json();
		try {
			result.setData(this.accountService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新角色数据发生异常", e);
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
			this.accountService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}