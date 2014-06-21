package com.examw.wechat.controllers.server;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.Json;
import com.examw.wechat.model.IRemoteTextValue;
import com.examw.wechat.model.mgr.RegisterInfo;
import com.examw.wechat.model.settings.CatalogInfo;
import com.examw.wechat.model.settings.ExamInfo;
import com.examw.wechat.model.settings.ProvinceInfo;
import com.examw.wechat.service.mgr.IRegisterService;
import com.examw.wechat.service.settings.ICatalogService;
import com.examw.wechat.service.settings.IExamService;
import com.examw.wechat.service.settings.IProvinceService;

/**
 * 提供远程服务控制器。
 * @author yangyong.
 * @since 2014-06-21.
 */
@Controller
@RequestMapping(value = "/remote")
public class RemoteServiceController {
	private static Logger logger = Logger.getLogger(RemoteServiceController.class);
	/**
	 * 省份服务。
	 */
	@Resource
	private IProvinceService provinceService;
	/**
	 * 考试类别数据接口
	 */
	@Resource
	private ICatalogService catalogService;
	/**
	 * 考试数据接口
	 */
	@Resource
	private IExamService examService;
	/**
	 * 登记用户服务。
	 */
	@Resource
	private IRegisterService registerService;
	/**
	 * 加载省份服务数据。
	 * @return
	 */
	@RequestMapping(value="/provinces", method = RequestMethod.GET)
	@ResponseBody
	public List<IRemoteTextValue> loadProvince(){
		return this.convert(this.provinceService.datagrid(new ProvinceInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getPage() {return null;}
			@Override
			public Integer getRows() {return null;}
			@Override
			public String getSort() {return "orderNo";}
			@Override
			public String getOrder() {return "asc";}
		}).getRows());
	}
	/**
	 * 加载考试类别服务数据。
	 * @return
	 */
	@RequestMapping(value="/catalogs", method = RequestMethod.GET)
	@ResponseBody
	public List<IRemoteTextValue> loadCatalogs(){ 
		return this.convert(this.catalogService.datagrid(new CatalogInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getPage() {return null;}
			@Override
			public Integer getRows() {return null;}
			@Override
			public String getSort() {return "orderNo";}
			@Override
			public String getOrder() {return "asc";}
		}).getRows());
	}
	/**
	 * 加载考试类别服务数据。
	 * @return
	 */
	@RequestMapping(value="/exams/{catalogId}", method = RequestMethod.GET)
	@ResponseBody
	public List<IRemoteTextValue> loadExams(final @PathVariable String catalogId){ 
		return this.convert(this.examService.datagrid(new ExamInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getPage() {return null;}
			@Override
			public Integer getRows() {return null;}
			@Override
			public String getCatalogId(){return catalogId;}
			@Override
			public String getSort() {return "orderNo";}
			@Override
			public String getOrder() {return "asc";}
		}).getRows());
	}
	/**
	 * 更新用户注册数据。
	 * @param info
	 * 用户注册数据。
	 * @return
	 * 更新结果。
	 */
	@RequestMapping(value="/register/update", method = RequestMethod.POST)
	@ResponseBody
	public Json updateRegister(RegisterInfo info, HttpServletRequest request){
		Json result = new Json();
		try {
			info.setIP(request.getRemoteAddr());
			this.registerService.update(info);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新登记用户数据发生异常", e);
		}
		return result;
	}
	/**
	 * 类型转换。
	 * @param list
	 * @return
	 */
	private List<IRemoteTextValue> convert(List<?> list){
		List<IRemoteTextValue> results = new ArrayList<>();
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				results.add((IRemoteTextValue)list.get(i));
			}
		}
		return results;
	}
}