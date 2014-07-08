package com.examw.wechat.controllers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.aware.IUserAware;
import com.examw.wechat.service.security.IMenuService;

/**
 * 管理后台首页。
 * @author yangyong.
 * @since 2014-04-25.
 */
@Controller
public class IndexController implements IUserAware {
	@Resource
	private IMenuService menuService;
	private String userId,userName,userNickName;
	/*
	 * 设置用户ID.
	 * @see com.examw.aware.IUserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/*
	 * 设置用户名称。
	 * @see com.examw.aware.IUserAware#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/*
	 * 设置用户昵称。
	 * @see com.examw.aware.IUserAware#setUserNickName(java.lang.String)
	 */
	@Override
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	/**
	 * 获取首页。
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"","/index","/"}, method = RequestMethod.GET)
	public String index(Model model){
		model.addAttribute("systemName", this.menuService.loadSystemName());
		return "/index";
	}
	/**
	 * 获取顶部
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String top(Model model){
		model.addAttribute("USER_ID", this.userId);
		model.addAttribute("USER_NAME", this.userName);
		model.addAttribute("USER_NICKNAME", this.userNickName);
		return "/top";
	}
	/**
	 * 获取左边
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String left(Model model){ 
		model.addAttribute("modules", this.menuService.loadModules());
		return "/left";
	}
	/**
	 * 获取默认工作页面。
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/center", method = RequestMethod.GET)
	public String defaultWorkspace(Model model){
		return "/workspace";
	}
}