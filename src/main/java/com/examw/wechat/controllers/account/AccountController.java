package com.examw.wechat.controllers.account;

import java.util.ArrayList;
import java.util.List;

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
import com.examw.model.TreeNode;
import com.examw.wechat.domain.account.Account;
import com.examw.wechat.domain.security.Right;
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
	//公众号管理服务。
	@Resource
	private IAccountService accountService;
	/**
	 * 列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.ACCOUNTS_ACCOUNT + ":" + Right.VIEW})
	@RequestMapping(value = {"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("PER_UPDATE", ModuleConstant.ACCOUNTS_ACCOUNT + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.ACCOUNTS_ACCOUNT + ":" + Right.DELETE);
		
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
	@RequiresPermissions({ModuleConstant.ACCOUNTS_ACCOUNT + ":" + Right.UPDATE})
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
	@RequiresPermissions({ModuleConstant.ACCOUNTS_ACCOUNT + ":" + Right.VIEW})
	@RequestMapping(value = "/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<AccountInfo> datagrid(AccountInfo info){
		return this.accountService.datagrid(info);
	}
	/**
	 * 加载全部微信公众账号。
	 * @return
	 */
	@RequestMapping(value = "/all-tree", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<TreeNode> allTree(){
		List<TreeNode> results = new ArrayList<>();
		List<AccountInfo> list = this.accountService.loadAllAccounts();
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				AccountInfo info = list.get(i);
				if(info == null) continue;
				TreeNode tn = new TreeNode();
				 tn.setId(info.getId());
				 tn.setText(info.getName());
				 results.add(tn);
			}
		}
		return results;
	}
	/**
	 * 加载全部微信公众号数据。
	 * @return
	 */
	@RequestMapping(value = "/all", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<AccountInfo> all(){
		return this.accountService.loadAllAccounts();
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.ACCOUNTS_ACCOUNT + ":" + Right.UPDATE})
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
	@RequiresPermissions({ModuleConstant.ACCOUNTS_ACCOUNT + ":" + Right.DELETE})
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