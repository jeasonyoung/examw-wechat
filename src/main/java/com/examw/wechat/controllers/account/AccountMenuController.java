package com.examw.wechat.controllers.account;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.Json;
import com.examw.model.TreeNode;
import com.examw.wechat.domain.security.Right;
import com.examw.wechat.model.account.AccountMenuInfo;
import com.examw.wechat.service.account.IAccountMenuService;

/**
 * 微信公众号菜单控制器。
 * @author yangyong.
 * @since 2014-05-17.
 */
@Controller
@RequestMapping(value = "/accounts/menu")
public class AccountMenuController {
	private static Logger logger = Logger.getLogger(AccountMenuController.class);
	//微信公众号菜单服务接口。 
	@Resource
	private IAccountMenuService accountMenuService;
	/**
	 * 列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.ACCOUNTS_MENU + ":" + Right.VIEW})
	@RequestMapping(value = {"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("PER_UPDATE", ModuleConstant.ACCOUNTS_MENU + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.ACCOUNTS_MENU + ":" + Right.DELETE);
		return "/accounts/menu_list";
	}
	/**
	 * 编辑页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.ACCOUNTS_MENU + ":" + Right.VIEW})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(){
		return "/accounts/menu_edit";
	}
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.ACCOUNTS_MENU + ":" + Right.VIEW})
	@RequestMapping(value = "/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public List<AccountMenuInfo>  datagrid(AccountMenuInfo info){
		List<AccountMenuInfo> list = this.accountMenuService.loadMenus(info.getAccountId(), null);
		if(list == null) list = new ArrayList<>();
		return list;
	}
	/**
	 * 加载菜单树数据。
	 * @return
	 */
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeNode> tree(AccountMenuInfo info){
		List<TreeNode> results = new ArrayList<>();
		List<AccountMenuInfo> list = this.accountMenuService.loadMenus(info.getAccountId(), info.getId());
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				TreeNode e = this.createTreeNode(list.get(i));
				if(e != null)results.add(e);
			}
		}
		return results;
	}
	/**
	 * 创建节点。
	 * @param info
	 * @return
	 */
	private TreeNode createTreeNode(AccountMenuInfo info){
		if(info == null) return null;
		TreeNode node = new TreeNode();
		node.setId(info.getId());
		node.setText(info.getName());
		if(info.getChildren() != null){
			List<TreeNode> children = new ArrayList<>();
			for(AccountMenuInfo m: info.getChildren()){
				TreeNode n = this.createTreeNode(m);
				if(n != null) children.add(n);
			}
			if(children.size() > 0)node.setChildren(children);
		}
		return node;
	}
	/**
	 * 更新数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.ACCOUNTS_MENU + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(AccountMenuInfo info){
		Json result = new Json();
		try {
			result.setData(this.accountMenuService.update(info));
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
	@RequiresPermissions({ModuleConstant.ACCOUNTS_MENU + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		Json result = new Json();
		try {
			this.accountMenuService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
	/**
	 * 查询公众号上的菜单。
	 * @param accountId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.ACCOUNTS_MENU + ":" + Right.VIEW})
	@RequestMapping(value="/query/{accountId}", method = RequestMethod.POST)
	@ResponseBody
	public String query(@PathVariable String accountId){
		return this.accountMenuService.queryMenus(accountId);
	}
	/**
	 * 创建公众号上的菜单。
	 * @param accountId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.ACCOUNTS_MENU + ":" + Right.UPDATE})
	@RequestMapping(value="/create/{accountId}", method = RequestMethod.POST)
	@ResponseBody
	public String create(@PathVariable String accountId){
		return this.accountMenuService.createMenus(accountId);
	}
	/**
	 * 删除公众号上的菜单。
	 * @param accountId
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.ACCOUNTS_MENU + ":" + Right.DELETE})
	@RequestMapping(value="/remove/{accountId}", method = RequestMethod.POST)
	@ResponseBody
	public String remove(@PathVariable String accountId){
		return this.accountMenuService.deleteMenus(accountId);
	}
}