package com.examw.wechat.controllers.security;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json; 
import com.examw.wechat.domain.security.Right;
import com.examw.wechat.model.security.MenuRightInfo;
import com.examw.wechat.model.security.RightInfo;
import com.examw.wechat.service.security.IMenuRightService;
import com.examw.wechat.service.security.IRightService;
/**
 * 菜单权限控制器。
 * @author yangyong.
 * @since 2014-05-04.
 */
@Controller
@RequestMapping(value = "/security/menu/right")
public class MenuRightController {
	private static final Logger logger = Logger.getLogger(MenuRightController.class);
	//权限服务。
	@Resource
	private IRightService rightService;
	//菜单权限服务。
	@Resource
	private IMenuRightService menuRightService;
	/**
	 * 列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.VIEW})
	@RequestMapping(value = {"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.DELETE);
		return "security/menuright_list";
	}
	/**
	 * 添加页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.UPDATE})
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String edit(String menuId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载添加页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.UPDATE);
		 model.addAttribute("rights", this.rightService.datagrid(new RightInfo(){
				private static final long serialVersionUID = 1L;
				@Override
				public Integer getPage(){return null;}
				@Override
				public Integer getRows(){return null;}
			}).getRows());
		 model.addAttribute("menuId", StringUtils.isEmpty(menuId) ? "" : menuId);
		return "security/menuright_add";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<MenuRightInfo> datagrid(MenuRightInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.menuRightService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(MenuRightInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			if(StringUtils.isEmpty(info.getMenuId())){
				result.setSuccess(false);
				result.setMsg("未获取菜单ID数据！");
				return result;
			}
			if(StringUtils.isEmpty(info.getRightId())){
				result.setSuccess(false);
				result.setMsg("未获取权限ID数据！");
				return result;
			}
			String[] menuIds = info.getMenuId().split("\\|"), rightIds = info.getRightId().split("\\|");
			for(int i = 0; i < menuIds.length; i++){
				if(StringUtils.isEmpty(menuIds[i])) 
					continue;
				for(int j = 0; j < rightIds.length; j++){
					if(StringUtils.isEmpty(rightIds[j]))
						continue;
					MenuRightInfo data = new MenuRightInfo();
					data.setMenuId(menuIds[i]);
					data.setRightId(rightIds[j]);
					
					this.menuRightService.update(data);
				}
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新菜单权限时发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		if(logger.isDebugEnabled()) logger.debug("删除数据［"+id+"］...");
		Json result = new Json();
		try {
			this.menuRightService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}