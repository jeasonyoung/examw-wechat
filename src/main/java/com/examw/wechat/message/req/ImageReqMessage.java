package com.examw.wechat.message.req;
/**
 * 图片消息。
 * @author yangyong.
 * @since 2014-02-20.
 * */
public class ImageReqMessage extends BaseReqMessage {
	private static final long serialVersionUID = 1L;
	private String picUrl,mediaId;
	/**
	 * 获取图片链接。
	 * @return 图片链接。
	 * */
	public String getPicUrl() {
		return picUrl;
	}
	/**
	 * 设置图片链接。
	 * @param picUrl
	 * 	图片链接。
	 * */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	/**
	 * 获取图片消息媒体id,
	 * 	用于调用多媒体文件下载接口拉取数据。
	 * @return 图片消息媒体id.
	 * */
	public String getMediaId() {
		return mediaId;
	}
	/**
	 * 设置图片消息媒体id。
	 * 用于调用多媒体文件下载接口拉取数据。
	 * @param mediaId
	 * 	图片消息媒体id。
	 * */
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
}