package com.examw.wechat.dao.settings;

import java.util.List;

import com.examw.wechat.dao.IBaseDao;
import com.examw.wechat.domain.settings.Province;
import com.examw.wechat.model.settings.ProvinceInfo;

/**
 * 省份数据访问接口。
 * @author yangyong.
 * @since 2014-06-20.
 */
public interface IProvinceDao extends IBaseDao<Province> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 */
	List<Province> findProvinces(ProvinceInfo info);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 */
	Long total(ProvinceInfo info);
}