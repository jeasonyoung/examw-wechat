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
import com.examw.wechat.domain.security.Right;
import com.examw.wechat.model.mgr.RecordInfo;
import com.examw.wechat.service.mgr.IRecordService;

/**
 * 消息记录控制器。
 * @author yangyong.
 * @since 2014-07-02.
 */
@Controller
@RequestMapping(value ="/mgr/record")
public class RecordController {
	private static final Logger logger = Logger.getLogger(RecordController.class);
	//消息记录服务接口。
	@Resource
	private IRecordService recordService;
	/**
	 * 列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.MGR_RECORD + ":" + Right.VIEW})
	@RequestMapping(value = {"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("PER_UPDATE", ModuleConstant.MGR_RECORD + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.MGR_RECORD + ":" + Right.DELETE);
		
		return "mgr/record_list";
	}
	/**
	 * 查询数据。
	 * @param info
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.MGR_RECORD + ":" + Right.VIEW})
	@RequestMapping(value = "/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<RecordInfo> datagrid(RecordInfo info){
		return this.recordService.datagrid(info);
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.MGR_RECORD + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		Json result = new Json();
		try {
			this.recordService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}