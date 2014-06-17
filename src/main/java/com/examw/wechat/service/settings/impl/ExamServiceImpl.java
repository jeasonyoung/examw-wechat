package com.examw.wechat.service.settings.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.wechat.dao.settings.ICatalogDao;
import com.examw.wechat.dao.settings.IExamDao;
import com.examw.wechat.domain.settings.Catalog;
import com.examw.wechat.domain.settings.Exam;
import com.examw.wechat.model.settings.CatalogInfo;
import com.examw.wechat.model.settings.ExamInfo;
import com.examw.wechat.service.impl.BaseDataServiceImpl;
import com.examw.wechat.service.settings.IExamService;

/**
 * 考试服务接口实现类
 * @author fengwei.
 * @since 2014年4月29日 上午11:59:52.
 */
public class ExamServiceImpl extends BaseDataServiceImpl<Exam, ExamInfo> implements IExamService {
	private IExamDao examDao;
	private ICatalogDao catalogDao;
	/** 
	 * 设置 考试设置数据接口
	 * @param examDao
	 * 考试数据接口
	 */
	public void setExamDao(IExamDao examDao) {
		this.examDao = examDao;
	}
	/**
	 * 设置考试类别数据接口。
	 * @param catalogDao
	 * 考试类别数据接口。
	 */
	public void setCatalogDao(ICatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Exam> find(ExamInfo info) {
		return this.examDao.findExams(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected ExamInfo changeModel(Exam data) {
		if(data == null) return null;
		ExamInfo info = new ExamInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getCatalog() != null){
			info.setCatalogId(data.getCatalog().getId());
			info.setCatalogName(data.getCatalog().getName());
		}
		return info;
	}
	/*
	 * 查询统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(ExamInfo info) {
		return this.examDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public ExamInfo update(ExamInfo info) {
		if(info == null) return null;
		boolean isAdded = false;
		Exam data = StringUtils.isEmpty(info.getId()) ? null : this.examDao.load(Exam.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Exam();
		}
		BeanUtils.copyProperties(info, data);
		if(!StringUtils.isEmpty(info.getCatalogId()) && (data.getCatalog() == null || !data.getCatalog().getId().equalsIgnoreCase(info.getCatalogId()))){
			Catalog catalog = this.catalogDao.load(Catalog.class, info.getCatalogId());
			if(catalog != null) data.setCatalog(catalog);
		}
		if(data.getCatalog() != null){
			info.setCatalogName(data.getCatalog().getName());
		}
		if(isAdded)this.examDao.save(data);
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
			Exam data = this.examDao.load(Exam.class, ids[i]);
			if(data != null){
				this.examDao.delete(data);
			}
		}
	}
	/*
	 * 根据考试设置ID获取考试类别信息
	 * @see com.examw.netplatform.service.admin.settings.ISubjectService#loadSubject(java.lang.String)
	 */
	@Override
	public CatalogInfo loadCatalog(String examId) {
		 if(StringUtils.isEmpty(examId)) return null;
		 Exam data = this.examDao.load(Exam.class, examId);
		 if(data == null || data.getCatalog() == null) return null;
		 CatalogInfo info = new CatalogInfo();
		 BeanUtils.copyProperties(data.getCatalog(), info);
		 return info;
	}
}