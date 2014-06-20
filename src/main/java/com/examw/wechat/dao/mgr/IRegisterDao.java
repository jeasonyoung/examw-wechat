package com.examw.wechat.dao.mgr;

import java.util.List;

import com.examw.wechat.dao.IBaseDao; 
import com.examw.wechat.domain.mgr.Register; 
import com.examw.wechat.model.mgr.RegisterInfo;

/**
 * 登记用户数据接口。
 * @author yangyong.
 * @since 2014-06-20.
 */
public interface IRegisterDao extends IBaseDao<Register> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据集合。
	 */
	List<Register> findRegisters(RegisterInfo info);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 */
	Long total(RegisterInfo info);
}