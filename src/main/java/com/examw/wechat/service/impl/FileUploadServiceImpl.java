package com.examw.wechat.service.impl;

import java.io.File; 
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.DigestUtils; 
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import com.examw.wechat.service.IFileUploadService;
/**
 * 文件上传服务实现。
 * @author yangyong.
 * @since 2014-05-01.
 */
public class FileUploadServiceImpl implements IFileUploadService {
	private static Logger logger = Logger.getLogger(FileUploadServiceImpl.class);
	private String storagePath;
	/**
	 * 设置存储路径。
	 * @param storagePath
	 * 存储路径。
	 */
	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}
	/*
	 * 上传文件。
	 * @see com.examw.wechat.service.IFileUploadService#upload(java.lang.String, byte[], java.lang.String)
	 */
	@Override
	public synchronized String upload(byte[] data,String root,String dirName,String ext) throws IOException {
		String err = null;
		if(data == null || data.length == 0){
			logger.error(err = "上传文件数据为空！");
			throw new RuntimeException(err);
		}
		if(StringUtils.isEmpty(ext)){
			logger.error(err = "上传文件后缀名！");
			throw new RuntimeException(err);
		}
		if(StringUtils.isEmpty(this.storagePath)){
			logger.error(err = "为配置文件存储路径[storagePath]！");
			throw new RuntimeException(err);
		}
		StringBuilder builder = new StringBuilder();
		builder.append(File.separator)
		 		  .append(this.storagePath)
				  .append(File.separatorChar);
		if(!StringUtils.isEmpty(dirName)){
			builder .append(dirName).append(File.separatorChar);
		}
		builder.append(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
				  .append(File.separatorChar); 
		
		File uploadDir = new File(root + builder.toString()); 
		//检查目录
		if(!uploadDir.exists()){
			uploadDir.mkdirs();
		}
		//检查目录写权限
		if(!uploadDir.canWrite()){
			logger.error(err = "上传目录没有写权限。");
			throw new RuntimeException(err);
		}
		builder.append(DigestUtils.md5DigestAsHex(data)).append(ext.toLowerCase());
		FileCopyUtils.copy(data, new File(root + builder.toString()));
		return builder.toString();
	}
	/*
	 * 加载文件管理器。
	 * @see com.examw.wechat.service.IFileUploadService#loadFilesManager(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> loadFilesManager(String root,String rootUrl,String dirName, String path, String order) {
		StringBuilder builder = new StringBuilder();
		builder.append(File.separator)
		 		  .append(this.storagePath)
				  .append(File.separatorChar);
		if(!StringUtils.isEmpty(dirName)){
			builder .append(dirName).append(File.separatorChar);
		}
		builder.append(path);
		File uploadDir = new File(root + builder.toString()); 
		//检查目录
		if(!uploadDir.exists()){
			uploadDir.mkdirs();
		}
		String current_path = root + builder.toString(),
				  current_url =  rootUrl + builder.toString(),
				  current_dir_path = path,
				  moveupDirPath = "";
		if(!StringUtils.isEmpty(path)){
			String str = current_dir_path.substring(0, current_dir_path.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}
		//不允许使用..移动到上一级目录
		if (path.indexOf("..") >= 0) {
			logger.error("Access is not allowed.");
			return null;
		}
		if(!StringUtils.isEmpty(path) && !path.endsWith("/")){
			logger.error("Parameter is not valid.");
			return null;
		}
		//目录不存在或不是目录
		File currentPathFile = new File(current_path);
		if(!currentPathFile.isDirectory()){
			logger.error("Directory does not exist.");
			return null;
		}
		//遍历目录取的文件信息
		List<Hashtable<String, Object>> fileList = new ArrayList<>();
		if(currentPathFile.listFiles() != null){
			for(File file : currentPathFile.listFiles()){
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if(file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if(file.isFile()){
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String>asList("gif","jpg","jpeg","png","bmp").contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}
		if(!StringUtils.isEmpty(order) && order.equalsIgnoreCase("size")){
			Collections.sort(fileList, new SizeComparator());
		}else if(!StringUtils.isEmpty(order) && order.equalsIgnoreCase("type")){
			Collections.sort(fileList, new TypeComparator());
		}else{
			Collections.sort(fileList, new NameComparator());
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("moveup_dir_path", moveupDirPath);
		result.put("current_dir_path", current_dir_path);
		result.put("current_url", current_url);
		result.put("total_count", fileList.size());
		result.put("file_list", fileList);
		return result;
	}
	private class NameComparator implements Comparator<Hashtable<String, Object>>{
		@Override
		public int compare(Hashtable<String, Object> o1, Hashtable<String, Object> o2) {
			if (((Boolean)o1.get("is_dir")) && !((Boolean)o2.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)o1.get("is_dir")) && ((Boolean)o2.get("is_dir"))) {
				return 1;
			} else {
				return ((String)o1.get("filename")).compareTo((String)o2.get("filename"));
			}
		}
	}
	private class SizeComparator implements Comparator<Hashtable<String, Object>>{
		@Override
		public int compare(Hashtable<String, Object> o1, Hashtable<String, Object> o2) {
			if (((Boolean)o1.get("is_dir")) && !((Boolean)o2.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)o1.get("is_dir")) && ((Boolean)o2.get("is_dir"))) {
				return 1;
			} else {
				if (((Long)o1.get("filesize")) > ((Long)o2.get("filesize"))) {
					return 1;
				} else if (((Long)o1.get("filesize")) < ((Long)o2.get("filesize"))) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}
	private class TypeComparator implements Comparator<Hashtable<String, Object>>{
		@Override
		public int compare(Hashtable<String, Object> o1, Hashtable<String, Object> o2) {
			if (((Boolean)o1.get("is_dir")) && !((Boolean)o2.get("is_dir"))) {
				return -1;
			} else if (!((Boolean)o1.get("is_dir")) && ((Boolean)o2.get("is_dir"))) {
				return 1;
			} else {
				return ((String)o1.get("filetype")).compareTo((String)o2.get("filetype"));
			}
		}	
	}
}