package com.examw.wechat.message.resp;

import com.examw.wechat.message.BaseMessage;
import com.examw.wechat.message.Music;
/**
 * 响应回复音乐消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class MusicRespMessage extends BaseRespMessage {
	private static final long serialVersionUID = 1L;
	private Music Music;
	/**
	 * 构造函数。
	 * */
	public MusicRespMessage(){
		super();
		this.setMsgType("music");
	}
	/**
	 * 构造函数。
	 * @param req
	 * 	请求消息。
	 * */
	public MusicRespMessage(BaseMessage req){
		super(req);
		this.setMsgType("music");
	}
	/**
	 * 获取音乐信息。
	 * @return 音乐信息。
	 * */
	public Music getMusic() {
		return Music;
	}
	/**
	 * 设置音乐信息。
	 * @param music
	 * 	音乐信息。	
	 * */
	public void setMusic(Music music) {
		this.Music = music;
	}
}