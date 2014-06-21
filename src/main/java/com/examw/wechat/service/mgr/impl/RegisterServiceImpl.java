package com.examw.wechat.service.mgr.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.wechat.dao.mgr.IRegisterDao;
import com.examw.wechat.dao.settings.IExamDao;
import com.examw.wechat.dao.settings.IProvinceDao;
import com.examw.wechat.domain.mgr.Register;
import com.examw.wechat.domain.settings.Exam;
import com.examw.wechat.domain.settings.Province;
import com.examw.wechat.model.mgr.RegisterInfo;
import com.examw.wechat.service.impl.BaseDataServiceImpl;
import com.examw.wechat.service.mgr.IRegisterService;

/**
 * 登记用户服务实现。
 * @author yangyong.
 * @since 2014-06-20.
 */
public class RegisterServiceImpl extends BaseDataServiceImpl<Register, RegisterInfo> implements IRegisterService {
	private IRegisterDao registerDao;
	private IProvinceDao provinceDao;
	private IExamDao examDao;
	/**
	 * 设置登记用户数据接口。
	 * @param registerDao
	 * 登记用户数据接口。
	 */
	public void setRegisterDao(IRegisterDao registerDao) {
		this.registerDao = registerDao;
	}
	/**
	 * 设置省份数据接口。
	 * @param provinceDao
	 */
	public void setProvinceDao(IProvinceDao provinceDao) {
		this.provinceDao = provinceDao;
	}
	/**
	 * 设置考试数据接口。
	 * @param examDao
	 */
	public void setExamDao(IExamDao examDao) {
		this.examDao = examDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Register> find(RegisterInfo info) {
		return this.registerDao.findRegisters(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected RegisterInfo changeModel(Register data) {
		if(data == null) return null;
		RegisterInfo info = new RegisterInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getProvince() != null){
			info.setProvinceId(data.getProvince().getId());
			info.setProvinceName(data.getProvince().getName());
		}
		if(data.getExam() != null){
			info.setExamId(data.getExam().getId());
			info.setExamName(data.getExam().getName());
		}
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(RegisterInfo info) {
		return this.registerDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public RegisterInfo update(RegisterInfo info) {
		if(info == null) return null;
		boolean isAdd = false;
		Register data = StringUtils.isEmpty(info.getId()) ? null : this.registerDao.load(Register.class, info.getId());
		if(isAdd = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
				info.setCreateTime(new Date());
			}
			data = new Register();
		}
		if(!isAdd){
			if(data.getCreateTime() != null) info.setCreateTime(data.getCreateTime());
			if(!StringUtils.isEmpty(data.getIP())) info.setIP(data.getIP());
		}
		BeanUtils.copyProperties(info, data);
		if(!StringUtils.isEmpty(info.getProvinceId()) && (data.getProvince() == null || !data.getProvince().getId().equalsIgnoreCase(info.getProvinceId()))){
			Province p = this.provinceDao.load(Province.class, info.getProvinceId());
			if(p != null) data.setProvince(p);
		}
		if(!StringUtils.isEmpty(info.getExamId()) && (data.getExam() == null || !data.getExam().getId().equalsIgnoreCase(info.getExamId()))){
			Exam e = this.examDao.load(Exam.class, info.getExamId());
			if(e != null) data.setExam(e);
		}
		if(data.getProvince() != null){
			info.setProvinceName(data.getProvince().getName());
		}
		if(data.getExam() != null){
			info.setExamName(data.getExam().getName());
		}
		if(isAdd)this.registerDao.save(data);
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
			Register data = this.registerDao.load(Register.class, ids[i]);
			if(data != null) this.registerDao.delete(data);
		}
	}
}