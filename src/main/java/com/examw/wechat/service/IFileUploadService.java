package com.examw.wechat.service;

/**
 * 上传文件服务。
 * @author yangyong.
 * @since 2014-04-30.
 */
public interface IFileUploadService {
	/**
	 * 上传文件。
	 * @param fileName
	 * 文件名称。
	 * @param data
	 * 文件数据。
	 * @param root
	 * 根文件路径。
	 * @return
	 * 文件路径。
	 */
	String upload(String fileName,byte[] data, String root);
}