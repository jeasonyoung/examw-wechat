package com.examw.wechat.controllers.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.wechat.service.server.ICoreService;
import com.examw.wechat.support.MsgUtil;
/**
 * 微信核心服务控制器。
 * @author yangyong.
 * @since 2014-06-18;
 */
@Controller
@RequestMapping(value = "/wechat")
public class CoreServiceController {
	private static Logger logger = Logger.getLogger(CoreServiceController.class);
	@Resource
	private ICoreService coreService;
	/**
	 * 验证微信服务器验证。
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void doGet(HttpServletRequest request, HttpServletResponse response){
		long before = System.currentTimeMillis();
		try{
			logger.info("开始验证微信消息真实性..");
			//将请求、响应的编码均设置为utf-8(防止中文乱码)
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			//
			logger.info("请求url：" + request.getRequestURI());
			logger.info("请求源地址：" + request.getRemoteAddr());
			logger.info("请求主机：" + request.getRemoteHost());
			String signature = request.getParameter("signature"),//微信加密签名
					  timestamp = request.getParameter("timestamp"),//时间戳
					  nonce = request.getParameter("nonce"),//随机数
					  echostr = request.getParameter("echostr");//随机字符串
		
			logger.info("获取微信签名：" + signature);
			logger.info("获取微信时间戳：" + timestamp);
			logger.info("获取微信随机数：" + nonce);
			logger.info("获取微信随机字符串：" + echostr);
			boolean result = this.coreService.checkSignature(signature, timestamp, nonce);
			logger.info("验证结果：" + result);
			PrintWriter out = response.getWriter();
			out.write(result ? echostr :  " result:"+result);
			out.flush();
			out.close();
		}catch(Exception e){
			logger.error("验证微信服务器发来的消息时发生异常：" + e.getMessage(), e);
		}finally{
			logger.info("处理完毕！[耗时：" + (System.currentTimeMillis() - before) + " ms]");
			logger.info("完成验证微信消息真实性..");
		}
	}
	/**
	 * 处理微信服务器发来的消息。
	 * @param request
	 * @param response
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		long before = System.currentTimeMillis();
		try {
			//将请求、响应的编码均设置为utf-8(防止中文乱码)
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			//调用核心业务服务接收消息、处理消息。
			logger.info("接收请求，处理中...");
			logger.info("接收请求url：" + request.getRequestURI());
			logger.info("请求源地址：" + request.getRemoteAddr());
			logger.info("请求主机：" + request.getRemoteHost());
			String callback = this.coreService.processRequest(MsgUtil.parseXml(request));
			if(StringUtils.isEmpty(callback)) callback = "";
			logger.info("响应消息：\r\n" + callback );
			PrintWriter out = response.getWriter();
			out.print(callback);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("处理微信服务器发来的消息时发生异常：" + e.getMessage(), e);
		}finally{
			logger.info("处理完毕！[耗时：" + (System.currentTimeMillis() - before) + " ms]");
		}
	}
}