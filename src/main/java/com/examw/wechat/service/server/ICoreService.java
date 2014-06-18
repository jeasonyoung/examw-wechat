package com.examw.wechat.service.server;

import javax.servlet.http.HttpServletRequest;
/**
 * 微信核心业务服务接口。
 * @author yangyong.
 * @since 2014-02-24.
 * */
public interface ICoreService {
	/**
	 * 校验微信令牌(微信开发模式注册的)。
	 * @param signature
	 * 	微信加密签名
	 * @param timestamp
	 * 时间戳
	 * @param nonce
	 * 随机数
	 * @param echostr
	 * 随机字符串
	 * @return
	 * 如果验证成功就返回随机字符串，否则返回失败原因。
	 * */
	String checkSignature(String signature,String timestamp,String nonce, String echostr);
	/**
	 * 处理微信请求。
	 * @param req
	 * 	请求参数集合。
	 * @return 反馈结果。
	 * */
	String processRequest(HttpServletRequest req);
}