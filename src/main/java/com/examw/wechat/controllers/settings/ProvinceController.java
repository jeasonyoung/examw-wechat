package com.examw.wechat.controllers.settings;

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
import com.examw.wechat.domain.security.Right;
import com.examw.wechat.model.settings.ProvinceInfo;
import com.examw.wechat.service.settings.IProvinceService;

/**
 * 省份服务控制器。
 * @author yangyong.
 * @since 2014-06-20.
 */
@Controller
@RequestMapping(value = "/settings/province")
public class ProvinceController {
	private static Logger logger  = Logger.getLogger(ProvinceController.class);
	//省份服务。
	@Resource
	private IProvinceService provinceService;
	/**
	 * 列表页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_PROVINCE + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("PER_UPDATE", ModuleConstant.SETTINGS_PROVINCE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SETTINGS_PROVINCE + ":" + Right.DELETE);
		return "settings/province_list";
	}
	/**
	 * 编辑页面。
	 * @param model
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_PROVINCE + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(Model model){
		return "settings/province_edit";
	}
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_PROVINCE + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<ProvinceInfo> datagrid(ProvinceInfo info){
		return this.provinceService.datagrid(info);
	}
	/**
	 * 全部数据。
	 * @return
	 */
	@RequestMapping(value="/all", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public List<ProvinceInfo> all(){
		return this.provinceService.datagrid(new ProvinceInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getPage() {return null;}
			@Override
			public Integer getRows() {return null;}
			@Override
			public String getSort() {return "orderNo";}
			@Override
			public String getOrder() {return "asc";}
		}).getRows();
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_PROVINCE + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(ProvinceInfo info){
		Json result = new Json();
		try {
			result.setData(this.provinceService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新考试类型数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SETTINGS_PROVINCE + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		Json result = new Json();
		try {
			this.provinceService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}