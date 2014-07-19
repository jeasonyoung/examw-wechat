package com.examw.wechat.service.security.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.model.TreeNode;
import com.examw.wechat.dao.security.IMenuDao;
import com.examw.wechat.dao.security.IMenuRightDao;
import com.examw.wechat.dao.security.IRoleDao;
import com.examw.wechat.domain.security.Menu;
import com.examw.wechat.domain.security.MenuRight;
import com.examw.wechat.domain.security.Role;
import com.examw.wechat.model.security.MenuRightInfo;
import com.examw.wechat.model.security.RoleInfo;
import com.examw.wechat.service.impl.BaseDataServiceImpl;
import com.examw.wechat.service.security.IRoleService;
/**
 * 角色服务接口实现类。
 * @author yangyong.
 * @since 2014-05-06.
 */
public class RoleServiceImpl extends BaseDataServiceImpl<Role, RoleInfo> implements IRoleService {
	private static final Logger logger = Logger.getLogger(RoleServiceImpl.class);
	private IRoleDao roleDao;
	private IMenuDao menuDao;
	private IMenuRightDao menuRightDao;
	private Map<Integer, String> roleStatusName;
	/**
	 * 设置角色数据接口。
	 * @param roleDao
	 * 角色数据接口。
	 */
	public void setRoleDao(IRoleDao roleDao) {
		if(logger.isDebugEnabled()) logger.debug("设置角色数据接口...");
		this.roleDao = roleDao;
	}
	/**
	 * 设置菜单数据接口。
	 * @param menuDao
	 * 菜单数据接口。
	 */
	public void setMenuDao(IMenuDao menuDao) {
		if(logger.isDebugEnabled()) logger.debug("设置菜单数据接口...");
		this.menuDao = menuDao;
	}
	/**
	 * 设置菜单权限数据接口。
	 * @param menuRightDao
	 * 菜单权限数据接口。
	 */
	public void setMenuRightDao(IMenuRightDao menuRightDao) {
		if(logger.isDebugEnabled()) logger.debug("设置菜单权限数据接口...");
		this.menuRightDao = menuRightDao;
	}
	/**
	 * 设置角色状态名称。
	 * @param roleStatusName
	 * 角色状态名称。
	 */
	public void setRoleStatusName(Map<Integer, String> roleStatusName) {
		if(logger.isDebugEnabled()) logger.debug("设置角色状态名称集合...");
		this.roleStatusName = roleStatusName;
	}
	/*
	 * 获取状态名称。
	 * @see com.examw.netplatform.service.admin.IRoleService#getStatusName(int)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(logger.isDebugEnabled()) logger.debug("加载状态［"+ status +"］名称...");
		if(this.roleStatusName == null || this.roleStatusName.size() == 0) return null;
		return this.roleStatusName.get(status);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Role> find(RoleInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.roleDao.findRoles(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected RoleInfo changeModel(Role data) {
		if(logger.isDebugEnabled()) logger.debug("类型转换...");
		if(data == null) return null;
		RoleInfo info = new RoleInfo();
		BeanUtils.copyProperties(data, info);
		info.setStatusName(this.loadStatusName(info.getStatus()));
		return info;
	}
	/*
	 *  统计查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(RoleInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.roleDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public RoleInfo update(RoleInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Role data = StringUtils.isEmpty(info.getId()) ? null : this.roleDao.load(Role.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) {
				info.setId(UUID.randomUUID().toString());
			}
			data = new Role();
		}
		BeanUtils.copyProperties(info, data);
		if(StringUtils.isEmpty(info.getStatusName())){
			info.setStatusName(this.loadStatusName(info.getStatus()));
		}
		if(isAdded) this.roleDao.save(data);
		return info;
		
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			Role data = this.roleDao.load(Role.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug("更新数据：" + ids[i]);
				this.roleDao.delete(data);
			}
		}
	}
	/*
	 * 加载角色权限树。
	 * @see com.examw.netplatform.service.admin.IRoleService#loadRoleRightTree(java.lang.String)
	 */
	@Override
	public List<TreeNode> loadRoleRightTree(String roleId) {
		if(logger.isDebugEnabled()) logger.debug("加载角色权限树数据...");
		List<TreeNode> results = new ArrayList<>();
		List<Menu>  menus =  this.menuDao.findMenus();
		if(menus != null && menus.size() > 0){
			//角色权限。
			Map<String, String> rightsMap = new HashMap<>();
			Role role = this.roleDao.load(Role.class, roleId);
			if(role != null && role.getRights() != null){
				for(MenuRight mr : role.getRights()){
					if(mr == null || StringUtils.isEmpty(mr.getCode())) continue;
					rightsMap.put(mr.getId(), mr.getCode());
				}
			}
			//生成树结构。
			for(int i = 0; i < menus.size(); i++){
				TreeNode node = this.createNode(menus.get(i), rightsMap);
				if(node != null){
					results.add(node);
				}
			}
		}
		return results;
	}
	/**
	 * 创建树节点。
	 * @param menu
	 * @param rights
	 * @return
	 */
	private TreeNode createNode(Menu menu, Map<String, String> rights){
		if(menu == null || StringUtils.isEmpty(menu.getId()) || StringUtils.isEmpty(menu.getName())) return null;
		TreeNode e = new TreeNode();
		e.setId(menu.getId());
		e.setText(menu.getName());
		Map<String, Object> attrs = new HashMap<>();
		attrs.put("type", 0);//菜单。
		e.setAttributes(attrs);
		List<TreeNode> children = null;
		if(menu.getChildren() == null || menu.getChildren().size() == 0){//叶子节点菜单，添加权限。
			List<MenuRight> list = this.menuRightDao.findMenuRights(e.getId());//获取菜单的所有权限。
			if(list != null && list.size() > 0){
				children = new ArrayList<>();
				for(int i = 0; i < list.size(); i++){
					MenuRight mr = list.get(i);
					if(mr == null) continue;
					TreeNode  node = new TreeNode();
					node.setId(mr.getId());
					node.setText(menu.getName() + "-" + mr.getRight().getName());
					attrs = new HashMap<>();
					attrs.put("type", 1);//权限。
					attrs.put("auth", rights == null ? false : rights.containsKey(node.getId()));
					node.setAttributes(attrs);
					children.add(node);
				}
				if(children.size() > 0) e.setChildren(children);
			}
			return e;
		}
		//非叶子节点，进行递归。
		children = new ArrayList<>();
		for(Menu m : menu.getChildren()){
			TreeNode tn = this.createNode(m,rights);
			if(tn != null) children.add(tn);
		}
		//将子菜单添加到父节点。
		if(children.size() > 0)  e.setChildren(children); 
		return e;
	}
	/*
	 * 添加角色权限。
	 * @see com.examw.netplatform.service.admin.IRoleService#addRoleRight(java.lang.String, java.lang.String[])
	 */
	@Override
	public void addRoleRight(String roleId, String[] menuRightIds) {
		if(logger.isDebugEnabled()) logger.debug("添加角色［"+ roleId +"］权限...");
		if(StringUtils.isEmpty(roleId)) return;
		Role role = this.roleDao.load(Role.class, roleId);
		if(role == null) return;
		Set<MenuRight> rights = new HashSet<MenuRight>();
		if(menuRightIds != null  && menuRightIds.length > 0){
			for(int i = 0; i < menuRightIds.length; i++){
				if(StringUtils.isEmpty(menuRightIds[i])) continue;
				MenuRight mr = this.menuRightDao.load(MenuRight.class, menuRightIds[i]);
				if(mr == null || StringUtils.isEmpty(mr.getCode())) continue;
				if(!rights.contains(mr)){
					rights.add(mr);
				}
			}
		}
		role.setRights(rights);
		this.roleDao.update(role);
	}
	/*
	 * 初始化角色。
	 * @see com.examw.wechat.service.security.IRoleService#init()
	 */
	@Override
	public void init(String roleId) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("初始化角色...");
		String err = null;
		if(StringUtils.isEmpty(roleId)){
			err = "RoleID 为空!";
			if(logger.isDebugEnabled()) logger.debug(err);
			throw new Exception(err);
		}
		List<MenuRight> menuRights = this.menuRightDao.findMenuRights(new MenuRightInfo(){private static final long serialVersionUID = 1L;});
		if(menuRights == null || menuRights.size() == 0){
			err = "未找到菜单权限！";
			if(logger.isDebugEnabled()) logger.debug(err);
			throw new Exception(err);
		}
		boolean isAdded = false;
		Role data = this.roleDao.load(Role.class, roleId);
		if(isAdded = (data == null)){
			data = new Role();
			data.setId(roleId);
		}
		data.setName("administrators");
		data.setDescription("系统初始化角色");
		data.setStatus(Role.STATUS_ENABLED);
		data.setRights(new HashSet<MenuRight>(menuRights));
		if(isAdded)this.roleDao.save(data);
		if(logger.isDebugEnabled()) logger.debug("初始化角色成功！");
	}
}