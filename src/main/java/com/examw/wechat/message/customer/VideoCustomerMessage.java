package com.examw.wechat.message.customer;

import com.examw.wechat.message.Video;

/**
 * 发送视频客服消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class VideoCustomerMessage extends BaseCustomerMessage {
	private static final long serialVersionUID = 1L;
	private Video video;
	/**
	 * 获取视频信息。
	 * @return 视频信息。
	 * */
	public Video getVideo() {
		return video;
	}
	/**
	 * 设置视频信息。
	 * @param video
	 * 	视频信息。
	 * */
	public void setVideo(Video video) {
		this.video = video;
	}
}