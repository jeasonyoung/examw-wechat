package com.examw.wechat.controllers.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.Json;
import com.examw.model.TreeNode;
import com.examw.wechat.domain.security.Right;
import com.examw.wechat.model.security.MenuInfo;
import com.examw.wechat.service.security.IMenuService;
/**
 * 菜单管理控制器。
 * @author yangyong.
 * @since 2014-04-28.
 */
@Controller
@RequestMapping(value = "/security/menu")
public class MenuController {
	private static Logger logger = Logger.getLogger(MenuController.class);
	@Resource
	private IMenuService menuService;
	/**
	 * 菜单列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU + ":" + Right.VIEW})
	@RequestMapping(value = {"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("PER_UPDATE", ModuleConstant.SECURITY_MENU + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SECURITY_MENU + ":" + Right.DELETE);
		return "security/menu_list";
	}
	/**
	 * 列表数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU + ":" + Right.VIEW})
	@RequestMapping(value = "/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public List<MenuInfo> datagrid(){
		return this.menuService.loadMenus();
	}
	/**
	 * 菜单树结构数据。
	 * @return
	 */
	@RequestMapping(value = "/tree", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public synchronized List<TreeNode> tree(){
		List<TreeNode> result = new ArrayList<>();
		List<MenuInfo> list = this.menuService.loadMenus();
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				TreeNode tv = this.createTreeNode(list.get(i));
				if(tv != null){
					result.add(tv);
				}
			}
		}
		return result;
	}
	/**
	 * 创建数据树。
	 * @param info
	 * @return
	 */
	private TreeNode createTreeNode(MenuInfo info){
		if(info == null) return null;
		TreeNode tv = new TreeNode();
		tv.setId(info.getId());
		tv.setText(info.getName());
		if(info.getChildren() != null && info.getChildren().size() > 0){
			List<TreeNode> childs = new ArrayList<>();
			 for(MenuInfo m : info.getChildren()){
				  TreeNode node = this.createTreeNode(m);
				  if(node != null) childs.add(node);
			 }
			 tv.setChildren(childs);
		}
		return tv;
	}
	/**
	 * 初始化菜单数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU + ":" + Right.UPDATE})
	@RequestMapping(value = "/init", method = RequestMethod.POST)
	@ResponseBody
	public Json init(){
		Json result = new Json();
		try {
			this.menuService.init();
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("初始化菜单时发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU + ":" + Right.DELETE})
	@RequestMapping(value= "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		Json result = new Json();
		try {
			if(!StringUtils.isEmpty(id)){
				this.menuService.delete(id.split("\\|"));
				result.setSuccess(true);
			}else {
				result.setSuccess(false);
				result.setMsg("未选择要删除的数据！");
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据时发生异常", e);
		}
		return result;
	}
}