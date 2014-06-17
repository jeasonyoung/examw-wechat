package com.examw.wechat.service.account.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.wechat.dao.account.IAccountDao;
import com.examw.wechat.dao.account.IAccountMenuDao;
import com.examw.wechat.domain.account.Account;
import com.examw.wechat.domain.account.AccountMenu;
import com.examw.wechat.message.menu.Button;
import com.examw.wechat.message.menu.CommonButton;
import com.examw.wechat.message.menu.ComplexButton;
import com.examw.wechat.message.menu.Menu;
import com.examw.wechat.message.menu.UrlButton;
import com.examw.wechat.model.account.AccountMenuInfo;
import com.examw.wechat.service.account.IAccessTokenService;
import com.examw.wechat.service.account.IAccountMenuService;
import com.examw.wechat.support.HttpUtil;

/**
 * 微信公众号菜单服务实现类。
 * @author yangyong.
 * @since 2014-04-02.
 * */
public class AccountMenuServiceImpl implements IAccountMenuService {
	private static Logger logger = Logger.getLogger(AccountMenuServiceImpl.class);
	private IAccountMenuDao accountMenuDao;
	private IAccountDao accountDao;
	private IAccessTokenService accessTokenService;
	private String createUrl,queryUrl,deleteUrl;
	/**
	 * 设置公众号菜单数据接口。
	 * @param accountMenuDao
	 * 	公众号菜单数据接口。
	 * */
	public void setAccountMenuDao(IAccountMenuDao accountMenuDao) {
		this.accountMenuDao = accountMenuDao;
	}
	/**
	 * 设置微信公众号数据访问接口。
	 * @param accountDao
	 * 微信公众号数据访问接口。
	 * */
	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}
	/**
	 * 设置公众号全局唯一票据数据访问接口。
	 * @param accessTokenService
	 * 公众号全局唯一票据数据访问接口。
	 * */
	public void setAccessTokenService(IAccessTokenService accessTokenService) {
		this.accessTokenService = accessTokenService;
	}
	/**
	 * 设置创建菜单URL。
	 * @param createUrl
	 * 创建菜单URL。
	 * */
	public void setCreateUrl(String createUrl){
		this.createUrl = createUrl;
	}
	/**
	 * 设置查询菜单URL。
	 * @param queryUrl
	 * 	查询菜单URL。
	 * */
	public void setQueryUrl(String queryUrl){
		this.queryUrl = queryUrl;
	}
	/**
	 * 设置删除菜单URL。
	 * @param deleteUrl
	 * 删除菜单URL。
	 * */
	public void setDeleteUrl(String deleteUrl){
		this.deleteUrl = deleteUrl;
	}
	/**
	 * 对象类型转化。
	 * @param data
	 * @param ignoreId
	 * */
	protected AccountMenuInfo changeModel(AccountMenu data,String ignoreId){
		if(data == null) return null;
		AccountMenuInfo info = new AccountMenuInfo();
		BeanUtils.copyProperties(data, info,new String[]{"children"});
		if(data.getAccount() != null){
			info.setAccountId(data.getAccount().getId());
			info.setAccountName(data.getAccount().getName());
		}
		if(ignoreId != null && info.getId().equalsIgnoreCase(ignoreId)){
			return null;
		}
		if(data.getChildren() != null && data.getChildren().size() > 0){
			List<AccountMenuInfo> children = new ArrayList<>();
			for(AccountMenu child: data.getChildren()){
				AccountMenuInfo c = this.changeModel(child,ignoreId);
				if(c != null) children.add(c);
			}
			if(children.size() > 0) {
				Collections.sort(children, new Comparator<AccountMenuInfo>(){
					@Override
					public int compare(AccountMenuInfo o1, AccountMenuInfo o2) {
						 return o1.getOrderNo() - o2.getOrderNo();
					}
				});
				info.setChildren(children);
			}
		}
		return info;
	}
	/*
	 * 加载菜单。
	 * @see com.examw.wechat.service.account.IAccountMenuService#loadMenus(java.lang.String, java.lang.String)
	 */
	@Override
	public List<AccountMenuInfo> loadMenus(String accountId, String ignoreId) {
		List<AccountMenu> list = this.accountMenuDao.findMenus(accountId);
		if(list == null || list.size() == 0) return null;
		List<AccountMenuInfo> results = new ArrayList<>();
		for(int i = 0; i < list.size(); i++){
			AccountMenuInfo info = this.changeModel(list.get(i), ignoreId);
			if(info != null) results.add(info);
		}
		return results;
	}
	/*
	 * 更新数据。
	 * @see com.examw.wechat.service.account.IAccountMenuService#update(com.examw.wechat.model.account.AccountMenuInfo)
	 */
	@Override
	public AccountMenuInfo update(AccountMenuInfo info) {
		if(info == null) return null;
		AccountMenu data = StringUtils.isEmpty(info.getId()) ? null : this.accountMenuDao.load(AccountMenu.class, info.getId());
		boolean isAdded = false;
		if(isAdded = (data == null)){
			data = new AccountMenu();
			info.setId(UUID.randomUUID().toString());
		}
		if(info.getAccountId() != null && (data.getAccount() == null || !info.getAccountId().equalsIgnoreCase(data.getAccount().getId()))){
			Account account = this.accountDao.load(Account.class, info.getAccountId());
			if(account != null){
				data.setAccount(account);
			}
		}
		if(info.getPid() != null && !info.getPid().trim().isEmpty()){
			AccountMenu parent = this.accountMenuDao.load(AccountMenu.class, info.getPid());
			if(parent != null){
				data.setParent(parent);
			}
		}
		BeanUtils.copyProperties(info, data);
		if(isAdded)this.accountMenuDao.save(data);
		if(StringUtils.isEmpty(info.getAccountName()) && data.getAccount() != null){
			info.setAccountName(data.getAccount().getName());
		}
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.wechat.service.account.IAccountMenuService#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		 if(ids == null || ids.length == 0) return;
		 for(int i = 0; i < ids.length; i++){
			 if(ids[i] == null || ids[i].trim().isEmpty()) continue;
			 AccountMenu data = this.accountMenuDao.load(AccountMenu.class, ids[i]);
			 if(data != null){
				 this.accountMenuDao.delete(data);
			 }
		 }
	}
	/*
	 * 构建菜单。
	 * @see com.examw.wechat.service.account.IAccountMenuService#buildMenu(java.lang.String)
	 */
	@Override
	public synchronized Menu buildMenu(String accountId) {
		logger.info("构建公众号["+accountId+"]菜单对象...");
		if(StringUtils.isEmpty(accountId)) return null;
		List<AccountMenu> list = this.accountMenuDao.findMenus(accountId);
		if(list == null || list.size() == 0) return null;
		Menu menu = new Menu();
		List<Button> btns = new ArrayList<>(), children = null;
		for(int i = 0; i < list.size(); i++){
			if(i >=  Menu.MAX_FIRST_LEVEL) break;
			AccountMenu data = list.get(i);
			if(data == null) continue;
			if(data.getChildren() != null && data.getChildren().size() > 0){
				ComplexButton button = new ComplexButton();
				button.setName(data.getName());
				children = new ArrayList<>();
				for(AccountMenu w : data.getChildren()){
					if(w == null) continue;
					if(children.size() >= Menu.MAX_SECOND_LEVEL)break;
					Button btn = this.createButton(w);
					if(btn != null) children.add(btn);
				}
				button.setSub_button(children.toArray(new Button[0]));
				btns.add(button);
			}else{
				Button _btn = this.createButton(data);
				if(_btn != null) btns.add(_btn);
			}
		}
		menu.setButton(btns.toArray(new Button[0]));
		logger.info("菜单对象生成完成。");
		return menu;
	}
	/**
	 * 创建公众号菜单按钮。
	 * @param data
	 * 公众号菜单数据。
	 * @return
	 * 菜单按钮。
	 * */
	private Button createButton(AccountMenu data){
		if(data == null) return null;
		logger.info("菜单：" + data.getName());
		if(data.getType() == AccountMenu.MENU_TYPE_CLICK){//按钮菜单
			CommonButton btn = new CommonButton();
			btn.setName(data.getName());
			btn.setKey(data.getCode());
			logger.info("创建普通按钮菜单:key=" + btn.getKey());
			return btn;
		}
		if(data.getType() == AccountMenu.MENU_TYPE_VIEW){
			UrlButton btn = new UrlButton();
			btn.setName(data.getName());
			btn.setUrl(data.getUrl());
			logger.info("创建网页菜单:key=" + btn.getUrl());
			return btn;
		}
		logger.info("类型不能被解析：type=" + data.getType());
		return null;
	}
	/*
	 * 创建菜单。
	 * @see com.examw.wechat.service.account.IAccountMenuService#createMenus(java.lang.String)
	 */
	@Override
	public synchronized String createMenus(String accountId) {
		String result = null;
		try{
			logger.info("开始给微信公众号创建菜单...");
			if(StringUtils.isEmpty(accountId)){
				logger.info(result = "公众号ID为空！");
				return result;
			}
			String token = this.accessTokenService.loadAccessToken(accountId);
			if(StringUtils.isEmpty(token)){
				logger.info(result = "获取公众号票据失败！");
				return result;
			}
			String url = String.format(this.createUrl, token);
			if(StringUtils.isEmpty(url)){
				logger.info(result = "未配置创建菜单URL！");
				return result;
			}
			Menu menu = this.buildMenu(accountId);
			if(menu == null){
				logger.info(result = "未能获取到菜单数据！");
				return result;
			}
			ObjectMapper mapper = new ObjectMapper();
			String post = mapper.writeValueAsString(menu);
			result = HttpUtil.httpsRequest(url, "POST", post);
			if(StringUtils.isEmpty(result)){
				logger.error(result = "服务器未响应！");
				return result;
			}
		}catch(Exception e){
			logger.error(e);
			result = e.getMessage();
		}finally{
			logger.info("完成菜单创建。");
		}
		return result;
	}
	/*
	 * 删除菜单。
	 * @see com.examw.wechat.service.account.IAccountMenuService#deleteMenus(java.lang.String)
	 */
	@Override
	public String deleteMenus(String accountId) {
		String result = null;
		try{
			logger.info("开始给微信公众号删除菜单...");
			if(StringUtils.isEmpty(accountId)){
				logger.info(result = "公众号ID为空！");
				return result;
			}
			String token = this.accessTokenService.loadAccessToken(accountId);
			if(token == null || token.trim().isEmpty()){
				logger.info(result = "获取公众号票据失败！");
				return result;
			}
			String url = String.format(this.deleteUrl, token);
			if(url == null || url.trim().isEmpty()){
				logger.info(result = "未配置删除菜单URL！");
				return result;
			}
			result = HttpUtil.httpsRequest(url, "GET", null);
			if(StringUtils.isEmpty(result)){
				logger.error(result = "服务器未响应！");
				return result;
			}
		}catch(Exception e){
			logger.error(e);
			result = e.getMessage();
		}finally{
			logger.info("完成菜单删除。");
		}
		return result;
	}
	/*
	 * 查询菜单。
	 * @see com.examw.wechat.service.account.IAccountMenuService#queryMenus(java.lang.String)
	 */
	@Override
	public String queryMenus(String accountId) {
		String result = null;
		try{
			logger.info("开始从微信公众号查询菜单...");
			if(accountId == null || accountId.trim().isEmpty()){
				logger.info(result = "公众号ID为空！");
				return result;
			}
			String token = this.accessTokenService.loadAccessToken(accountId);
			if(token == null || token.trim().isEmpty()){
				logger.info(result = "获取公众号票据失败！");
				return result;
			}
			String url = String.format(this.queryUrl, token);
			if(url == null || url.trim().isEmpty()){
				logger.info(result = "未配置查询菜单URL！");
				return result;
			}
			result = HttpUtil.httpsRequest(url, "GET", null);
			if(StringUtils.isEmpty(result)){
				logger.error(result = "服务器未响应！");
				return result;
			}
		}catch(Exception e){
			logger.error(e);
			result = e.getMessage();
		}finally{
			logger.info("完成菜单查询。");
		}
		return result;
	}
}