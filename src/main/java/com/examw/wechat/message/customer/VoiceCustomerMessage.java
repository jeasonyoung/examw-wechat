package com.examw.wechat.message.customer;

import com.examw.wechat.message.Voice;

/**
 * 发送语音客服消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class VoiceCustomerMessage extends BaseCustomerMessage {
	private static final long serialVersionUID = 1L;
	private Voice voice;
	/**
	 * 获取语音信息。
	 * @return 语音信息。
	 * */
	public Voice getVoice() {
		return voice;
	}
	/**
	 * 设置语音信息。
	 * @param voice
	 * 	语音信息。
	 * */
	public void setVoice(Voice voice) {
		this.voice = voice;
	}
}