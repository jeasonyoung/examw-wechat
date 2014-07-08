package com.examw.wechat.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.core.NamedThreadLocal;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.examw.aware.IUserAware;
import com.examw.wechat.domain.security.User;
import com.examw.wechat.service.security.IUserService;
/**
 * 用户认证拦截器。
 * @author yangyong.
 * @since 2014-05-15.
 */
public class UserAuthenticationInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = Logger.getLogger(UserAuthenticationInterceptor.class);
	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<>("StopWatch-StartTime");
	private IUserService userService;
	/**
	 * 设置用户服务接口。
	 * @param userService
	 * 用户服务接口。
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	/*
	 * 在业务处理之前被调用。
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		if(logger.isDebugEnabled()){
			this.startTimeThreadLocal.set(System.currentTimeMillis());//线程绑定开始时间(该数据只有当前请求的线程可见)。
			logger.debug("开始业务处理..."+request.getServletPath());
		}
		if(handler instanceof HandlerMethod){
			HandlerMethod hm = (HandlerMethod)handler;
			if(hm != null && (hm.getBean() instanceof IUserAware)){
				IUserAware userAware = (IUserAware)hm.getBean();
				logger.info("准备注入用户信息...");
				Subject subject = SecurityUtils.getSubject();
				String account = (String)subject.getPrincipal();
				if(!StringUtils.isEmpty(account)){
					User user = this.userService.findByAccount(account);
					if(user != null){
						userAware.setUserId(user.getId());
						userAware.setUserName(user.getName());
						userAware.setUserNickName(user.getNickName());
						if(logger.isDebugEnabled())logger.debug(String.format("注入[%1$s]用户信息:id=%2$s;name=%3$s;nick=%4$s", account, user.getId(), user.getName(), user.getNickName()));
					}
				}
			}
		}
		return super.preHandle(request, response, handler);
	}
	/*
	 * 业务处理完全处理完后被调用。
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{
		super.afterCompletion(request, response, handler, ex);
		if(logger.isDebugEnabled()){
			long consumeTime = System.currentTimeMillis() - this.startTimeThreadLocal.get();
			logger.debug("业务"+request.getServletPath()+"处理完成，耗时：" + consumeTime + "  " + ((consumeTime > 500) ? "[较慢]" : "[正常]"));
		}
	}
}