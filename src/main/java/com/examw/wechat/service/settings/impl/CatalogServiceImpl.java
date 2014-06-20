package com.examw.wechat.service.settings.impl;
 
import java.util.List; 
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
 
import com.examw.wechat.dao.settings.ICatalogDao;
import com.examw.wechat.domain.settings.Catalog; 
import com.examw.wechat.model.settings.CatalogInfo;
import com.examw.wechat.service.impl.BaseDataServiceImpl;
import com.examw.wechat.service.settings.ICatalogService;
/**
 * 考试类型服务接口实现类。
 * @author yangyong.
 * @since 2014-04-28.
 */
public class CatalogServiceImpl extends BaseDataServiceImpl<Catalog, CatalogInfo> implements ICatalogService {
	private ICatalogDao catalogDao;
	/**
	 * 设置考试类型数据接口。
	 * @param catalogDao
	 * 考试类型数据接口。
	 */
	public void setCatalogDao(ICatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Catalog> find(CatalogInfo info) {
		 return this.catalogDao.findCatalogs(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected CatalogInfo changeModel(Catalog data) {
		if(data == null) return null;
		CatalogInfo info = new CatalogInfo();
		BeanUtils.copyProperties(data, info);
		return info;
	}
	/*
	 * 查询数据汇总。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(CatalogInfo info) {
		return this.catalogDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public CatalogInfo update(CatalogInfo info) {
		if(info == null) return null;
		boolean isAdded = false;
		Catalog data = StringUtils.isEmpty(info.getId()) ? null : this.catalogDao.load(Catalog.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Catalog();
		}
		BeanUtils.copyProperties(info, data);
		if(isAdded)this.catalogDao.save(data);
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			Catalog data = this.catalogDao.load(Catalog.class, ids[i]);
			if(data != null){
				this.catalogDao.delete(data);
			}
		}
	}
}