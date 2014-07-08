package com.examw.wechat.controllers;
 
import java.util.ArrayList;
import java.util.Arrays; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource; 
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller; 
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.bind.annotation.ResponseBody; 
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.examw.utils.IOUtil;
import com.examw.wechat.service.IFileUploadService;

/**
 * 文件上传控制器。
 * @author yangyong.
 * @since 2014-04-30.
 */
@Controller
@RequestMapping(value = "/uploads")
public class UploadController {
	private static final Logger logger = Logger.getLogger(UploadController.class);
	/**
	 * 文件上传服务接口。
	 */
	@Resource
	private IFileUploadService fileUploadService;
	//
	private static Map<String, String> extMap = new HashMap<>();
	static{
		extMap.put("image", ".gif,.jpg,.jpeg,.png,.bmp");
		extMap.put("flash", ".swf,.flv");
		extMap.put("media", ".swf,.flv,.mp3,.wav,.wma,.wmv,.mid,.avi,.mpg,.asf,.rm,.rmvb");
		extMap.put("file", ".doc,.docx,.xls,.xlsx,.ppt,.htm,.html,.txt,.zip,.rar,.gz,.bz2");
	}
	/**
	 * 文件上传。
	 * 支持uploadify.
	 * @param file
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upload(HttpServletRequest request){
		if(logger.isDebugEnabled())logger.debug("开始接收上传文件...");
		 Map<String, Object> result = new HashMap<>();
 		try {
 			String err = null;
 			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
 			if(multipartRequest == null){
 				logger.error(err = "未选择文件上传！");
 				throw new RuntimeException(err);
 			}
 			Map<String, MultipartFile> files =   multipartRequest.getFileMap();
 			if(files == null || files.size() == 0){
 				logger.error(err = "获得文件上传失败！");
 				throw new RuntimeException(err);
 			}
 			if(logger.isDebugEnabled())logger.debug("开始上传文件...");
 			String root = request.getSession().getServletContext().getRealPath(".");
 			String dirName = request.getParameter("dir");
 			if(StringUtils.isEmpty(dirName)){
 				dirName = "image";
 			}
 			if(!extMap.containsKey(dirName)){
 				logger.error(err = "目录名["+ dirName +"]不正确。");
 				throw new RuntimeException(err);
 			}
 			String rootUrl = request.getContextPath();
 			List<String> urls = new ArrayList<>();
 			for(Entry<String, MultipartFile> entry : files.entrySet()){
 			    	if(logger.isDebugEnabled())logger.debug("上传文件：" + entry.getKey());
	 				String ext = IOUtil.getExtension(entry.getValue().getOriginalFilename()).toLowerCase();
	 				if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(ext)){
	 					logger.error(err = "上传文件扩展名["+ext+"]是不允许的扩展名。只允许" + extMap.get(dirName) + "格式。");
	 					throw new RuntimeException(err);
	 				}
	 				String url = rootUrl + this.fileUploadService.upload(entry.getValue().getBytes(), root, dirName, ext);
	 				if(logger.isDebugEnabled())logger.debug("上传URL：" + url);
	 				urls.add(url);
 			} 
			result.put("error", 0);
			result.put("url",urls.size() == 1 ? urls.get(0) : urls.toArray(new String[0]));
			if(logger.isDebugEnabled())logger.debug("上传文件完成。");
 		} catch (Exception e) {
			logger.error("上传文件发生异常", e);
			result.put("error",1);
			result.put("message", e.getMessage());
			e.printStackTrace();
 		}
 		return result; 
	}
	/**
	 * 查询文件管理。
	 * @param dir
	 * @param path
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/manager", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> manager(HttpServletRequest request){
		try{
			if(logger.isDebugEnabled())logger.debug("查询文件管理...");
			String dirName = request.getParameter("dir");
			if(!StringUtils.isEmpty(dirName)){
				if(!Arrays.<String>asList(extMap.keySet().toArray(new String[0])).contains(dirName)){
					logger.error("Invalid Directory name.");
					return null;
				}
			}
			String path = request.getParameter("path");
			if(StringUtils.isEmpty(path)) path = "";
			String order = request.getParameter("order");
			if(StringUtils.isEmpty(order)){
				order = "name";
			}
			return this.fileUploadService.loadFilesManager(request.getSession().getServletContext().getRealPath("."),
																						 request.getContextPath(),
																						 dirName,
																						 path,
																						 order.toLowerCase());
		}catch(Exception e){
			logger.error("查询文件管理发生异常：" + e);
		}
		return null;
	}
}