package com.examw.wechat.message.resp;

import com.examw.wechat.message.BaseMessage;
import com.examw.wechat.message.Voice;
/**
 * 响应回复语音消息。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public class VoiceRespMessage extends BaseRespMessage {
	private static final long serialVersionUID = 1L;
	private Voice Voice;
	/**
	 * 构造函数。
	 * */
	public VoiceRespMessage(){
		super();
		this.setMsgType("voice");
	}
	/**
	 * 构造函数。
	 * @param req
	 * 	请求消息。
	 * */
	public VoiceRespMessage(BaseMessage req){
		super(req);
		this.setMsgType("voice");
	}
	/**
	 * 获取语音。
	 * @return 语音。
	 * */
	public Voice getVoice() {
		return Voice;
	}
	/**
	 * 设置语音。
	 * @param voice
	 * 语音。
	 * */
	public void setVoice(Voice voice) {
		this.Voice = voice;
	}
}