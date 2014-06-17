package com.examw.wechat.service.settings.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.wechat.dao.settings.IExamDao;
import com.examw.wechat.dao.settings.ISubjectDao;
import com.examw.wechat.domain.settings.Exam;
import com.examw.wechat.domain.settings.Subject;
import com.examw.wechat.model.settings.ExamInfo;
import com.examw.wechat.model.settings.SubjectInfo;
import com.examw.wechat.service.impl.BaseDataServiceImpl;
import com.examw.wechat.service.settings.ISubjectService;

/**
 * 科目服务接口实现类
 * @author fengwei.
 * @since 2014年4月29日 下午1:20:17.
 * 
 * 修改查询条件，优化代码结构。
 * @author yangyong.
 * @since 2014-05-24.
 */
public class SubjectServiceImpl  extends BaseDataServiceImpl<Subject, SubjectInfo> implements ISubjectService {
	private ISubjectDao subjectDao;
	private IExamDao examDao;
	private Map<Integer,String> typeMap;
	/**
	 * 设置考试数据接口。
	 * @param examDao
	 */
	public void setExamDao(IExamDao examDao) {
		this.examDao = examDao;
	}
	/**
	 * 设置 科目数据接口
	 * @param subjectDao
	 * 
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置 科目类型常量map 通过service配置文件进行注入
	 * @param typeMap
	 * 
	 */
	public void setTypeMap(Map<Integer, String> typeMap) {
		this.typeMap = typeMap;
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Subject> find(SubjectInfo info) {
		return this.subjectDao.findSubjects(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected SubjectInfo changeModel(Subject data) {
		if(data == null) return null;
		SubjectInfo info = new SubjectInfo();
		BeanUtils.copyProperties(data, info);
		//类型名称赋值
		info.setTypeName(this.getTypeName(info.getType()));
		if(data.getExam() != null){
			info.setExamId(data.getExam().getId());
			info.setExamName(data.getExam().getName());
			if(data.getExam().getCatalog() != null){
				info.setCatalogId(data.getExam().getCatalog().getId());
				info.setCatalogName(data.getExam().getCatalog().getName());
			}
		}
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(SubjectInfo info) {
		return this.subjectDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public SubjectInfo update(SubjectInfo info) {
		if(info == null) return null;
		boolean isAdded = false;
		Subject data = StringUtils.isEmpty(info.getId()) ? null : this.subjectDao.load(Subject.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Subject();
		}
		BeanUtils.copyProperties(info, data);
		//类型名称赋值
		info.setTypeName(getTypeName(info.getType()));
		//考试设置
		if(!StringUtils.isEmpty(info.getExamId()) && (data.getExam() == null || !data.getExam().getId().equalsIgnoreCase(info.getExamId()))){
			Exam exam = this.examDao.load(Exam.class, info.getExamId());
			if(exam != null) data.setExam(exam);
		}
		if(data.getExam() != null){
			info.setExamName(data.getExam().getName());
			if(data.getExam().getCatalog() != null){
				info.setCatalogId(data.getExam().getCatalog().getId());
				info.setCatalogName(data.getExam().getCatalog().getName());
			}
		}
		if(isAdded)this.subjectDao.save(data);
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
			if(StringUtils.isEmpty(ids[i])) continue;
			Subject data = this.subjectDao.load(Subject.class, ids[i]);
			if(data != null){
				this.subjectDao.delete(data);
			}
		}
	}
	/*
	 * 获取考试类型名称。
	 * @see com.examw.netplatform.service.admin.settings.ISubjectService#getTypeName(java.lang.Integer)
	 */
	@Override
	public String getTypeName(Integer type) {
		if(type==null) return null;
		if(typeMap==null) return null;
		return typeMap.get(type);
	}
	/*
	 * 根据科目ID获取考试信息。
	 * @see com.examw.netplatform.service.admin.settings.ISubjectService#loadExam(java.lang.String)
	 */
	@Override
	public ExamInfo loadExam(String subjectId) {
		if(StringUtils.isEmpty(subjectId)) return null;
		 Subject data = this.subjectDao.load(Subject.class, subjectId);
		 if(data == null || data.getExam() == null) return null;
		 ExamInfo info = new ExamInfo();
		 BeanUtils.copyProperties(data.getExam(), info);
		 if(data.getExam().getCatalog() != null){
			 info.setCatalogId(data.getExam().getCatalog().getId());
			 info.setCatalogName(data.getExam().getCatalog().getName());
		 }
		 return info;
	}
}