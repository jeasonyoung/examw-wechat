package com.examw.wechat.controllers.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.wechat.domain.security.User;
import com.examw.wechat.model.security.RoleInfo;
import com.examw.wechat.model.security.UserInfo;
import com.examw.wechat.service.security.IRoleService;
import com.examw.wechat.service.security.IUserService;
/**
 * 用户管理控制器。
 * @author yangyong.
 * @since 2014-05-09.
 */
@Controller
@RequestMapping(value = "/security/user")
public class UserController {
	private static Logger logger = Logger.getLogger(UserController.class);
	/**
	 * 用户服务接口。
	 */
	@Resource
	private IUserService userService;
	/**
	 * 角色服务接口。
	 */
	@Resource
	private IRoleService roleService;
	/**
	 * 获取列表页面。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		//model.addAttribute("PER_UPDATE", ModuleConstant.SECURITY_USER + ":" + Right.UPDATE);
		//model.addAttribute("PER_DELETE", ModuleConstant.SECURITY_USER + ":" + Right.DELETE);
		
		model.addAttribute("STATUS_ENABLED", this.userService.loadUserStatusName(User.STATUS_ENABLED));
		model.addAttribute("STATUS_DISABLE", this.userService.loadUserStatusName(User.STATUS_DISABLE));
		return "security/user_list";
	}
	/**
	 * 获取编辑页面。
	 * @param agencyId
	 * 所属培训机构。
	 * @param model
	 * 数据绑定。
	 * @return
	 * 编辑页面地址。
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(String agencyId,Model model){
		
		model.addAttribute("STATUS_ENABLED", this.userService.loadUserStatusName(User.STATUS_ENABLED));
		model.addAttribute("STATUS_DISABLE", this.userService.loadUserStatusName(User.STATUS_DISABLE));
		
		model.addAttribute("GENDER_MALE", this.userService.loadGenderName(User.GENDER_MALE));
		model.addAttribute("GENDER_FEMALE", this.userService.loadGenderName(User.GENDER_FEMALE));
		
		//model.addAttribute("USERTYPES", this.userService.loadUserTypes());
		
		DataGrid<RoleInfo> roles = this.roleService.datagrid(new RoleInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getPage(){return null;}
			@Override
			public Integer getRows(){return null;}
			@Override
			public String getSort(){
				return "name";
			}
			@Override
			public String getOrder() {
				return "asc";
			}
		});
		model.addAttribute("roles", roles.getRows());
		
		return "security/user_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<UserInfo> datagrid(UserInfo info){
		return this.userService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(UserInfo info, HttpServletRequest request){
		Json result = new Json();
		try {
			 info.setLastLoginIP(request.getRemoteAddr());
			 result.setData(this.userService.update(info));
			 result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新用户数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		Json result = new Json();
		try {
			this.userService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}