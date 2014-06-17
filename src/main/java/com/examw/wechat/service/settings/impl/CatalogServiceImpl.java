package com.examw.wechat.service.settings.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.model.TreeNode;
import com.examw.wechat.dao.settings.ICatalogDao;
import com.examw.wechat.domain.settings.Catalog;
import com.examw.wechat.domain.settings.Exam;
import com.examw.wechat.domain.settings.Subject;
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
	/*
	 * 加载考试类型下考试设置树数据。
	 * @see com.examw.netplatform.service.admin.settings.ICatalogService#loadCatalogExams()
	 */
	@Override
	public List<TreeNode> loadAllCatalogExams() {
		List<TreeNode> treeNodes = new ArrayList<>();
		List<Catalog> catalogs = this.find(new CatalogInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getPage() {return null;}
			@Override
			public Integer getRows() {return null;}
			@Override
			public String getOrder() {return "asc";}
			@Override
			public String getSort() {return "orderNo";};
		});
		if(catalogs != null && catalogs.size() > 0){
			Map<String, Object> attributes = new HashMap<>();
			for(Catalog catalog : catalogs){
				if(catalog == null || catalog.getExams() == null || catalog.getExams().size() == 0) continue;
				TreeNode node = new TreeNode();
				node.setId(catalog.getId());
				node.setText(catalog.getName());
				attributes = new HashMap<>();
				attributes.put("type", "catalog");
				node.setAttributes(attributes);
				List<TreeNode> childs = new ArrayList<>();
				for(final Exam e : catalog.getExams()){
					if(e == null) continue;
					childs.add(new TreeNode(){
						private static final long serialVersionUID = 1L;
						@Override
						public String getId(){return e.getId();}
						@Override
						public String getText(){return e.getName();}
						@Override
						public Map<String, Object> getAttributes(){
							Map<String, Object> attr = new HashMap<>();
							attr.put("type", "exam");
							return attr;
						}
					});
				}
				node.setChildren(childs);
				treeNodes.add(node);
			}
		}
		return treeNodes;
	}
	/*
	 * 加载全部考试科目数据树。
	 * @see com.examw.netplatform.service.admin.settings.ICatalogService#loadAllSubjects()
	 */
	@Override
	public List<TreeNode> loadAllCatalogExamSubjects() {
		List<Catalog> catalogs = this.find(new CatalogInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getPage() {return null;}
			@Override
			public Integer getRows() {return null;}
			@Override
			public String getOrder() {return "asc";}
			@Override
			public String getSort() {return "orderNo";};
		});
		List<TreeNode> list_catalogs = new ArrayList<>();
		if(catalogs != null && catalogs.size() > 0){
			Map<String, Object> attributes = new HashMap<>();
			for(Catalog catalog : catalogs){
				if(catalog == null || catalog.getExams() == null || catalog.getExams().size() == 0) continue;
				TreeNode tv_catalog = new TreeNode();
				tv_catalog.setId(catalog.getId());
				tv_catalog.setText(catalog.getName());
				attributes = new HashMap<>();
				attributes.put("type", "catalog");
				tv_catalog.setAttributes(attributes);
				List<TreeNode> list_exams = new ArrayList<>();
				for(Exam e : catalog.getExams()){
					if(e == null) continue;
					TreeNode tv_exam = new TreeNode();
					tv_exam.setId(e.getId());
					tv_exam.setText(e.getName());
					attributes = new HashMap<>();
					attributes.put("type", "exam");
					tv_exam.setAttributes(attributes);
					if(e.getSubjects() != null && e.getSubjects().size() > 0){
						List<TreeNode> list_subjects = new ArrayList<>();
						for(Subject s : e.getSubjects()){
							TreeNode tv_subject = new TreeNode();
							tv_subject.setId(s.getId());
							tv_subject.setText(s.getName());
							attributes = new HashMap<>();
							attributes.put("type", "subject");
							tv_subject.setAttributes(attributes);
							list_subjects.add(tv_subject);
						}
						tv_exam.setChildren(list_subjects);
					}
					list_exams.add(tv_exam);
				}
				tv_catalog.setChildren(list_exams);
				list_catalogs.add(tv_catalog);
			}
		}
		return list_catalogs;
	}
}