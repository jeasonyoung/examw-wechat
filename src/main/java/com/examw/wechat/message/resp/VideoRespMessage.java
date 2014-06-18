package com.examw.wechat.message.resp;

import com.examw.wechat.message.BaseMessage;
import com.examw.wechat.message.Video;
/**
 * 响应回复视频消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class VideoRespMessage extends BaseRespMessage {
	private static final long serialVersionUID = 1L;
	private Video Video;
	/**
	 * 构造函数。
	 * */
	public VideoRespMessage(){
		super();
		this.setMsgType("video");
	}
	/**
	 * 构造函数。
	 * @param req
	 * 	请求消息。
	 * */
	public VideoRespMessage(BaseMessage req){
		super(req);
		this.setMsgType("video");
	}
	/**
	 * 获取视频信息。
	 * @return 视频信息。
	 * */
	public Video getVideo() {
		return Video;
	}
	/**
	 * 设置视频信息。
	 * @param video
	 * 	视频信息。
	 * */
	public void setVideo(Video video) {
		this.Video = video;
	}
}