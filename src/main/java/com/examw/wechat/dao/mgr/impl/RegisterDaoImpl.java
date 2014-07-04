package com.examw.wechat.dao.mgr.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.wechat.dao.impl.BaseDaoImpl;
import com.examw.wechat.dao.mgr.IRegisterDao;
import com.examw.wechat.domain.mgr.Register;
import com.examw.wechat.model.mgr.RegisterInfo;

/**
 * 登记用户数据接口实现。
 * @author yangyong.
 * @since 2014-06-20.
 */
public class RegisterDaoImpl extends BaseDaoImpl<Register> implements IRegisterDao {
	/*
	 * 查询数据。
	 * @see com.examw.wechat.dao.mgr.IRegisterDao#findRegisters(com.examw.wechat.model.mgr.RegisterInfo)
	 */
	@Override
	public List<Register> findRegisters(RegisterInfo info) {
		String hql = "from Register r where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by r." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.wechat.dao.mgr.IRegisterDao#total(com.examw.wechat.model.mgr.RegisterInfo)
	 */
	@Override
	public Long total(RegisterInfo info) {
		String hql = "select count(*) from Register r where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//查询条件。
	private String addWhere(RegisterInfo info, String hql,Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getExamId())){
			hql += " and (r.exam.id = :examId or r.exam.catalog.id = :examId)";
			parameters.put("examId", info.getExamId());
		}
		if(!StringUtils.isEmpty(info.getProvinceId())){
			hql += " and (r.province.id = :provinceId)";
			parameters.put("provinceId", info.getProvinceId());
		}
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and ((r.name like :name) or (r.mobile like :name) or (r.qq like :name))";
			parameters.put("name", "%" + info.getName() + "%");
		}
		return hql;
	}
	/*
	 * 加载登录用户信息。
	 * @see com.examw.wechat.dao.mgr.IRegisterDao#loadRegister(java.lang.String)
	 */
	@Override
	public Register loadRegister(String mobile) {
		if(StringUtils.isEmpty(mobile)) return null;
		final String hql = "from Register r where r.mobile = :mobile order by r.createTime desc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("mobile", mobile);
		List<Register> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null; 
		return list.get(0);
	}
}