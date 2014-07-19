package com.examw.wechat.service.security.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.wechat.dao.security.IRightDao;
import com.examw.wechat.domain.security.Right;
import com.examw.wechat.model.security.RightInfo;
import com.examw.wechat.service.impl.BaseDataServiceImpl;
import com.examw.wechat.service.security.IRightService;

/**
 * 基础权限服务接口实现。
 * @author yangyong.
 * @since 2014-05-03.
 */
public class RightServiceImpl extends BaseDataServiceImpl<Right,RightInfo> implements IRightService {
	private static final Logger logger = Logger.getLogger(RightServiceImpl.class);
	private IRightDao rightDao;
	private Map<Integer, String> rightNameMap;
	/**
	 * 设置基础权限数据接口。
	 * @param rightDao
	 * 基础权限数据接口。
	 */
	public void setRightDao(IRightDao rightDao) {
		if(logger.isDebugEnabled()) logger.debug("设置基础权限数据接口...");
		this.rightDao = rightDao;
	}
	/**
	 * 设置权限名称。
	 * @param rightNameMap
	 * 权限名称。
	 */
	public void setRightNameMap(Map<Integer, String> rightNameMap) {
		if(logger.isDebugEnabled()) logger.debug("设置权限名称集合");
		this.rightNameMap = rightNameMap;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Right> find(RightInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.rightDao.findRights(info);
	}
	/*
	 * 数据类型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected RightInfo changeModel(Right data) {
		if(logger.isDebugEnabled()) logger.debug("类型转换...");
		if(data == null) return null;
		RightInfo info = new RightInfo();
		BeanUtils.copyProperties(data, info);
		return info;
	}
    /*
     * 查询数据总数。
     * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
     */
	@Override
	protected Long total(RightInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.rightDao.total(info);
	}
    /*
     * 更新数据。
     * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
     */
	@Override
	public RightInfo update(RightInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null || StringUtils.isEmpty(info.getId())) return null;
		boolean isAdded = false;
		Right data =  this.rightDao.load(Right.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Right();
		}
		BeanUtils.copyProperties(info, data);
		if(isAdded)this.rightDao.save(data);
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
			Right data = this.rightDao.load(Right.class, ids[i]);
			if(data != null) {
				if(logger.isDebugEnabled()) logger.debug("删除数据：" + data.getName());
				this.rightDao.delete(data);
			}
		}
	}
	/*
	 * 初始化数据。
	 * @see com.examw.netplatform.service.admin.IRightService#init()
	 */
	@Override
	public void init() throws Exception {
		if(logger.isDebugEnabled()) logger.debug("初始化权限数据...");
		//查看
		if(logger.isDebugEnabled()) logger.debug("初始化查看权限.");
		this.update(new RightInfo(((Integer)Right.VIEW).toString(), this.loadRightName(Right.VIEW), Right.VIEW));
		//修改
		if(logger.isDebugEnabled()) logger.debug("初始化修改权限.");
		this.update(new RightInfo(((Integer)Right.UPDATE).toString(), this.loadRightName(Right.UPDATE), Right.UPDATE));
		//删除
		if(logger.isDebugEnabled()) logger.debug("初始化删除权限.");
		this.update(new RightInfo(((Integer)Right.DELETE).toString(), this.loadRightName(Right.DELETE), Right.DELETE));
	}
	/*
	 * 加载权限名称。
	 * @see com.examw.netplatform.service.admin.IRightService#getRightName(int)
	 */
	@Override
	public String loadRightName(Integer right) {
		if(logger.isDebugEnabled()) logger.debug("加载权限名称［right="+ right +"］");
		if(this.rightNameMap == null || this.rightNameMap.size() == 0) return null;
		return this.rightNameMap.get(right);
	}
}