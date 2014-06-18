package com.examw.wechat.message.customer;

import com.examw.wechat.message.Music;

/**
 * 发送音乐客服消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class MusicCustomerMessage extends BaseCustomerMessage {
	private static final long serialVersionUID = 1L;
	private Music music;
	/**
	 * 获取音乐信息。
	 * @return 音乐信息。
	 * */
	public Music getMusic() {
		return music;
	}
	/**
	 * 设置音乐信息。
	 * @param music
	 * 	音乐信息。
	 * */
	public void setMusic(Music music) {
		this.music = music;
	}
}