package com.examw.wechat.service.impl;

import java.io.File; 
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.util.DigestUtils; 
import org.springframework.util.FileCopyUtils;

import com.examw.utils.IOUtil;
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
	
	@Override
	public synchronized String upload(String fileName, byte[] data,String root) {
		if(this.storagePath == null || this.storagePath.trim().isEmpty()){
			logger.error("为配置文件存储路径[storagePath]！");
			return null;
		}
		if(fileName == null || fileName.trim().isEmpty()){
			logger.error("上传文件名称为null!");
			return null;
		}
		if(data == null || data.length == 0){
			logger.error("上传文件数据为空！");
			return null;
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder builder = new StringBuilder();
		builder.append(File.separator)
		 		  .append(this.storagePath)
				  .append(File.separatorChar)
				  .append(sf.format(new Date()))
				  .append(File.separatorChar);
		
		File dir = new File(root + builder.toString());
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		builder.append(DigestUtils.md5DigestAsHex(data))
				  .append(IOUtil.getExtension(fileName).toLowerCase());
		
		try {
			FileCopyUtils.copy(data, new File(root + builder.toString()));
		} catch (IOException e) {
			logger.error("上传文件存储时发生异常", e);
			e.printStackTrace();
		}
		return builder.toString();
	}
}