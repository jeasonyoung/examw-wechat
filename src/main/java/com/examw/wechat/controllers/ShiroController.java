package com.examw.wechat.controllers;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.Json;
import com.examw.utils.VerifyCodeUtil;
import com.examw.wechat.service.security.ILoginLogService;
import com.examw.wechat.service.security.IMenuService;

/**
 * Shiro 控制器。
 * @author yangyong.
 * @since 2014-05-14.
 */
@Controller
//@RequestMapping(value={"/wechat"})
public class ShiroController {
	private static Logger logger = Logger.getLogger(ShiroController.class);
	private static final String SESSION_KEY_VERIFYCODE = "verifyCode";
	private ObjectMapper mapper = new ObjectMapper();
	//菜单服务接口。
	@Resource
	private IMenuService menuService;
	//登录日志服务接口。
	@Resource
	private ILoginLogService loginLogService;
	{
		mapper.setSerializationInclusion(Inclusion.NON_NULL);
	}
	/**
	 * 获取登录页面。
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model){
		model.addAttribute("systemName", this.menuService.loadSystemName());
		return "shiro/login";
	}
	/**
	 * 获取验证码图片。
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/verifyCodeImage", method = RequestMethod.GET)
	public void verifyCodeImage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		logger.info("开始生成验证码...");
		//设置页面不缓存。
		response.setHeader("Pragme", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		//生成验证码。
		String code = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_ONLY, 4, null);
		//将验证码放到HttpSession里面。
		request.getSession().setAttribute(SESSION_KEY_VERIFYCODE, code);
		logger.info("本次生成验证码为[" + code +"],已存放到Http Session中。");
		//设置输出的内容为JPEG图像
		response.setContentType("image/jpeg");
		BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(code, 90, 30, 3, true, Color.WHITE, Color.RED, null);
		
		ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
	}
	/**
	 * 用户身份认证。
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Json authentication(HttpServletRequest request){
		Json result = new Json();
		try{
			String account = WebUtils.getCleanParam(request, "account"),
					  password = WebUtils.getCleanParam(request, "password"),
					  submitCode = WebUtils.getCleanParam(request, "verifycode");
			//获取HttpSession中的验证码。
			String code = (String)request.getSession().getAttribute(SESSION_KEY_VERIFYCODE);
			logger.info("用户["+ account +"]登录时输入的验证码为["+ submitCode +"],HttpSession中的验证码为["+ code+"]");
			if(StringUtils.isEmpty(submitCode) ||  !submitCode.equalsIgnoreCase(code)){
				throw new RuntimeException("验证码不正确！");
			}
			UsernamePasswordToken token = new UsernamePasswordToken(account, password);
			logger.info("为了验证登录用户而封装的token为:" + this.mapper.writeValueAsString(token));
			token.setRememberMe(false);
			//获取当前的Subject
			Subject subject = SecurityUtils.getSubject();
			logger.info("对用户["+ account +"]进行登录验证.....验证开始.");
			subject.login(token);
			logger.info("对用户["+ account +"]进行登录验证.....验证通过.");
			result.setSuccess(true);
		    String browser =	request.getHeader("User-Agent");
		    if(!StringUtils.isEmpty(browser) && browser.length() > 255){
		    	browser = browser.substring(0, 254);
		    }
			this.loginLogService.addLog(account, request.getRemoteAddr(), browser);
		}catch(UnknownAccountException e){
			logger.info("登录验证未通过:未知账户," + e.getMessage());
			result.setSuccess(false);
			result.setMsg("未知账户");
		}catch(IncorrectCredentialsException e){
			logger.info("登录验证未通过:密码不正确," + e.getMessage());
			result.setSuccess(false);
			result.setMsg("密码不正确");
		}catch(LockedAccountException e){
			logger.info("登录验证未通过:账户已锁定," + e.getMessage());
			result.setSuccess(false);
			result.setData(0);
			result.setMsg("账户已锁定");
		}catch(ExcessiveAttemptsException e){
			logger.info("登录验证未通过:用户名或密码错误次数过多," + e.getMessage());
			result.setSuccess(false);
			result.setMsg("用户名或密码错误次数过多");
		}catch(AuthenticationException e){
			logger.info("登录验证未通过:用户名或密码不正确," + e.getMessage());
			result.setSuccess(false);
			result.setMsg("用户名或密码不正确");
		}catch(Exception e){
			logger.info("登录验证未通过:" + e.getMessage());
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	/**
	 * 用户登出。
	 * @return
	 */
	@RequestMapping(value="/logout")
	public String logout(){
		SecurityUtils.getSubject().logout();
		return "redirect:/index";
	}
}