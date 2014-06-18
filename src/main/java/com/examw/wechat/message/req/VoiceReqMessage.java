package com.examw.wechat.message.req;
/**
 * 语音消息。
 * @author yangyong.
 * @since 2014-02-21.
 **/
public class VoiceReqMessage extends BaseReqMessage {
	private static final long serialVersionUID = 1L;
	private String mediaId,format;
	/**
	 * 获取语音消息媒体id,
	 *  可以调用多媒体文件下载接口拉取数据。
	 * @return 语音消息媒体id。
	 * */
	public String getMediaId() {
		return mediaId;
	}
	/**
	 * 设置语音消息媒体id。
	 * @param mediaId
	 * 	语音消息媒体id。
	 * */
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	/**
	 * 获取语音格式。
	 * @return 语音格式(如amr,speex等)。
	 * */
	public String getFormat() {
		return format;
	}
	/**
	 * 设置语音格式。
	 * @param format
	 * 语音格式(如amr,speex等)。
	 * */
	public void setFormat(String format) {
		this.format = format;
	}
}