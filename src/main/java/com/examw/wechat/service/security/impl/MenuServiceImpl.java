package com.examw.wechat.service.security.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.xml.sax.SAXException;

import com.examw.configuration.ModuleDefine;
import com.examw.configuration.ModuleDefineCollection;
import com.examw.configuration.ModuleSystem;
import com.examw.configuration.ModuleSystemCollection; 
import com.examw.wechat.dao.security.IMenuDao;
import com.examw.wechat.domain.security.Menu;
import com.examw.wechat.model.security.MenuInfo;
import com.examw.wechat.service.security.IMenuService;
/**
 * 菜单服务。
 * @author yangyong
 * @since 2014-04-28.
 */
public class MenuServiceImpl  implements IMenuService {
	private static Logger logger = Logger.getLogger(MenuServiceImpl.class);
	private IMenuDao menuDao;
	private String menuFile,systemId;
	private static Map<String, ModuleSystem> mapSystemCache = Collections.synchronizedMap(new HashMap<String,ModuleSystem>());
	/**
	 * 设置菜单数据接口。
	 * @param menuDao
	 * 菜单数据接口。
	 */
	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}
	/**
	 * 设置菜单文件。
	 * @param menuFile
	 * 菜单文件。
	 */
	public void setMenuFile(String menuFile) {
		this.menuFile = menuFile;
	}
	/**
	 * 设置菜单文件中系统ID。
	 * @param systemId
	 * 系统ID。
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	/**
	 * 加载文件解析为对象集合。
	 * @return
	 * 对象集合。
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	private synchronized ModuleSystemCollection loadFileToParse(){
		if(StringUtils.isEmpty(this.menuFile)){
			if(logger.isDebugEnabled()) logger.debug("菜单文件为空！");
			return null;
		}
		Resource rs = new ClassPathResource(this.menuFile, ClassUtils.getDefaultClassLoader());
		if(rs != null) {
			try {
				ModuleSystemCollection collection = ModuleSystemCollection.parse(rs.getInputStream());
				if(collection != null && collection.size() > 0) return collection;
			} catch (SAXException | IOException | ParserConfigurationException e) {
				logger.error("加载文件解析为对象集合发生异常:", e);
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 加载系统菜单数据。
	 * @return
	 */
	@Override
	public synchronized ModuleSystem loadSystem() {
		if(StringUtils.isEmpty(this.systemId)){
			if(logger.isDebugEnabled()) logger.debug("系统ID为空！");
			return null;
		}
		ModuleSystem ms = mapSystemCache.get(this.systemId);
		if(ms == null){
			ModuleSystemCollection collection = this.loadFileToParse();
			if(collection != null && collection.size() > 0){
				 for(ModuleSystem system : collection){
					 if(system != null && system.getId().equalsIgnoreCase(this.systemId)){
						 ms = system;
						 break;
					 }
				 }
				 if(ms != null){
					 mapSystemCache.put(this.systemId, ms);
				 }
			}
		}
		return ms;
	}
	/**
	 * 更新数据。
	 * @param info
	 * 
	 * @return
	 */
	@Override
	public MenuInfo update(MenuInfo info) {
		if(info == null || StringUtils.isEmpty(info.getId())) return null;
		boolean isAdded = false;
		Menu data = this.menuDao.load(Menu.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Menu();
		}
		BeanUtils.copyProperties(info, data);
		if(!StringUtils.isEmpty(info.getPid()) && (data.getParent() == null || !data.getParent().getId().equalsIgnoreCase(info.getPid()))){
			Menu parent = this.menuDao.load(Menu.class, info.getPid());
			if(parent != null)data.setParent(parent);
		}
		if(isAdded) this.menuDao.save(data);
		return info;
	}
	/**
	 * 删除数据。
	 * @param ids
	 */
	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			Menu data = this.menuDao.load(Menu.class, ids[i]);
			if(data != null && (data.getChildren() == null || data.getChildren().size() == 0)){
				this.menuDao.delete(data); 
				if(logger.isDebugEnabled()) logger.debug("删除数据:" + data.getName());
			}
		}
	}
	/**
	 * 加载菜单数据。
	 */
	@Override
	public List<MenuInfo> loadMenus() {
		List<MenuInfo> results = new ArrayList<>();
		List<Menu> list = this.menuDao.findMenus();
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				MenuInfo info = this.changeModel(list.get(i));
				if(info != null) results.add(info);
			}
		}
		return results;
	}
	/**
	 * 数据类型转换。
	 * @param data
	 * 实体对象。
	 * @return
	 * 结果数据。
	 */
	protected MenuInfo changeModel(Menu data) {
		if(data == null) return null;
		MenuInfo info = new MenuInfo();
		BeanUtils.copyProperties(data, info, new String[] {"children"});
		if(data.getChildren() != null && data.getChildren().size() > 0){
			List<MenuInfo> children = new ArrayList<>();
			for(Menu m : data.getChildren()){
				MenuInfo c = this.changeModel(m);
				if(c != null){
					c.setPid(data.getId());
					children.add(c);
				}
			}
			info.setChildren(children);
		}
		return info;
	}
	/**
	 * 初始化菜单数据。
	 */
	@Override
	public void init() throws Exception {
		if(logger.isDebugEnabled()) logger.debug("开始初始化菜单数据...");
		String err = null;
		ModuleSystem system = this.loadSystem();
		if(system == null || system.getModules() == null || system.getModules().size() == 0){
			err = "菜单文件中没有系统信息或菜单数据信息！";
			if(logger.isDebugEnabled()) logger.debug(err);
			throw new Exception(err);
		}
		
		for(ModuleDefine module : system.getModules()){
			this.initModuleToMenu(null, module);
		}
		if(logger.isDebugEnabled()) logger.debug("初始化完成！");
	}
	/**
	 * 初始化菜单模块到菜单数据。
	 * @param pid
	 * 上级菜单。
	 * @param module
	 * 菜单模块。
	 */
	private void initModuleToMenu(String pid, ModuleDefine module){
		if(module == null) return;
		MenuInfo info = new MenuInfo(); 
		BeanUtils.copyProperties(module, info);
		info.setOrderNo(module.getOrder());
		if(! StringUtils.isEmpty(pid)){
			info.setPid(pid);
		}
		if(this.update(info) == null) return;
		if(module.getModules() != null && module.getModules().size() > 0){
			for(ModuleDefine child : module.getModules()){
				this.initModuleToMenu(info.getId(), child);
			}
		}
	}
	/*
	 * 加载系统名称。
	 * @see com.examw.netplatform.service.admin.IMenuService#loadSystemName()
	 */
	@Override
	public String loadSystemName() {
		ModuleSystem ms = this.loadSystem();
		return ms == null ? null : ms.getName();
	}
	/*
	 *加载系统模块集合。
	 * @see com.examw.netplatform.service.admin.IMenuService#loadModules()
	 */
	@Override
	public ModuleDefineCollection loadModules() {
		ModuleSystem ms = this.loadSystem();
		return ms == null ? null : ms.getModules();
	}
}