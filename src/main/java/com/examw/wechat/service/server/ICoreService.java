package com.examw.wechat.service.server;

import java.util.Map;
/**
 * 微信核心业务服务接口。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public interface ICoreService {
	/**
	 * 请求消息微信用户OpenID。
	 */
	static final String REQ_MSG_FromUserName = "FromUserName";
	/**
	 * 请求消息公众号OpenID。
	 */
	static final String REQ_MSG_ToUserName = "ToUserName";
	/**
	 * 请求消息类型。
	 */
	static final String REQ_MSG_MsgType = "MsgType";
	/**
	 * 校验微信令牌(微信开发模式注册的)。
	 * @param signature
	 * 	微信加密签名
	 * @param timestamp
	 * 时间戳
	 * @param nonce
	 * 随机数
	 * @return
	 * 如果验证成功就返回true,否则false。
	 * */
	boolean checkSignature(String signature,String timestamp,String nonce) throws Exception;
	/**
	 * 处理微信请求。
	 * @param req
	 * 	请求参数集合。
	 * @return 反馈结果。
	 * */
	String processRequest(Map<String, String> req);
}