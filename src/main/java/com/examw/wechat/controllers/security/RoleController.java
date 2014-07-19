package com.examw.wechat.controllers.security;

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
import com.examw.wechat.domain.security.Right;
import com.examw.wechat.domain.security.Role;
import com.examw.wechat.model.security.RoleInfo;
import com.examw.wechat.service.security.IRoleService;

/**
 * 角色控制器。
 * @author yangyong.
 *
 */
@Controller
@RequestMapping(value = "/security/role")
public class RoleController {
	private static final Logger logger = Logger.getLogger(RoleController.class);
	//设置角色服务接口。
	@Resource
	private IRoleService roleService;
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SECURITY_ROLE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SECURITY_ROLE + ":" + Right.DELETE);
		return "security/role_list";
	}
	/**
	 * 获取编辑页面。
	 * @return
	 * 编辑页面。
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		model.addAttribute("STATUS_ENABLED", this.roleService.loadStatusName(Role.STATUS_ENABLED));
		model.addAttribute("STATUS_DISABLE", this.roleService.loadStatusName(Role.STATUS_DISABLE));
		return "security/role_edit";
	}
	/**
	 * 获取角色权限页面。
	 * @return
	 * 角色权限页面。
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.VIEW})
	@RequestMapping(value="/right", method = RequestMethod.GET)
	public String roleRight(String roleId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载授权页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SECURITY_ROLE + ":" + Right.UPDATE);
		model.addAttribute("roleId", roleId);
		return "security/role_right";
	}
	/**
	 * 获取全部的角色数据。
	 * @return
	 */
	@RequestMapping(value="/all", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<RoleInfo> all(){
		if(logger.isDebugEnabled()) logger.debug("加载全部角色数据...");
		return this.roleService.datagrid(new RoleInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getPage(){return null;}
			@Override
			public Integer getRows(){return null;}
		}).getRows();
	}
	/**
	 * 获取角色权限树。
	 * @param roleId
	 * 角色ID。
	 * @return
	 * 角色权限树。
	 */
	@RequestMapping(value="/right-tree", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeNode> roleRightTree(String roleId){
		if(logger.isDebugEnabled()) logger.debug("加载角色权限数据...");
		return this.roleService.loadRoleRightTree(roleId);
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<RoleInfo> datagrid(RoleInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.roleService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(RoleInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			result.setData(this.roleService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新角色数据发生异常", e);
		}
		return result;
	}
	/**
	 * 添加角色权限数据。
	 * @param roleId
	 *  角色ID。
	 * @param menuRightIds
	 * 菜单权限ID数组。
	 * @return
	 * 反馈信息。
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.UPDATE})
	@RequestMapping(value="/addroleright", method = RequestMethod.POST)
	@ResponseBody
	public Json addRoleRights(String roleId, String menuRightIds){
		if(logger.isDebugEnabled()) logger.debug("添加角色权限数据...");
		Json result = new Json();
		try {
			 this.roleService.addRoleRight(roleId, menuRightIds.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("添加角色权限数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据［"+ id +"］...");
		Json result = new Json();
		try {
			this.roleService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}