package com.examw.wechat.service;

import java.io.IOException;
import java.util.Map;

/**
 * 上传文件服务。
 * @author yangyong.
 * @since 2014-04-30.
 */
public interface IFileUploadService {
	/**
	 * 上传文件。
	 * @param data
	 * 文件数据。
	 * @param root
	 * 根文件路径。
	 * @param dirName
	 * 上传目录。
	 * @param ext
	 * 后缀名。
	 * @return
	 * 文件路径。
	 */
	String upload(byte[] data, String root,String dirName,String ext) throws IOException;
	/**
	 * 加载文件管理。
	 * @param root
	 * 根文件路径。
	 * @param rootUrl
	 * 根文件URL。
	 * @param dirName
	 * 上传目录。
	 * @param path
	 * 当前路径。
	 * @param order
	 * 排序。
	 * @return
	 */
	Map<String, Object> loadFilesManager(String root,String rootUrl,String dirName,String path,String order);
}