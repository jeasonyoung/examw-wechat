package com.examw.wechat.service.settings.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.wechat.dao.settings.IProvinceDao;
import com.examw.wechat.domain.settings.Province;
import com.examw.wechat.model.settings.ProvinceInfo;
import com.examw.wechat.service.impl.BaseDataServiceImpl;
import com.examw.wechat.service.settings.IProvinceService;

/**
 * 省份服务实现。
 * @author yangyong.
 * @since 2014-06-20.
 */
public class ProvinceServiceImpl extends BaseDataServiceImpl<Province,ProvinceInfo> implements IProvinceService {
	private IProvinceDao provinceDao;
	/**
	 * 设置省份数据访问接口。
	 * @param provinceDao
	 */
	public void setProvinceDao(IProvinceDao provinceDao) {
		this.provinceDao = provinceDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Province> find(ProvinceInfo info) {
		return this.provinceDao.findProvinces(info);
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(ProvinceInfo info) {
		return this.provinceDao.total(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected ProvinceInfo changeModel(Province data) {
		if(data == null) return null;
		ProvinceInfo info = new ProvinceInfo();
		BeanUtils.copyProperties(data, info);
		return info;
	}
	/*
	 * 更新数据。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public ProvinceInfo update(ProvinceInfo info) {
		if(info == null) return null;
		boolean isAdded = false;
		Province data = StringUtils.isEmpty(info.getId()) ? null : this.provinceDao.load(Province.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Province();
		}
		BeanUtils.copyProperties(info, data);
		if(isAdded)this.provinceDao.save(data);
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		 if(ids == null || ids.length == 0) return;
		 for(int i = 0; i < ids.length; i++){
			 if(StringUtils.isEmpty(ids[i])) continue;
			 Province data = this.provinceDao.load(Province.class, ids[i]);
			 if(data != null)this.provinceDao.delete(data);
		 }
	}
}