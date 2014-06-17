package com.examw.wechat.dao.settings;

import java.util.List;

import com.examw.wechat.dao.IBaseDao;
import com.examw.wechat.domain.settings.Subject;
import com.examw.wechat.model.settings.SubjectInfo;
/**
 * 科目数据接口
 * @author fengwei.
 * @since 2014年4月29日 上午11:50:52.
 */
public interface ISubjectDao extends IBaseDao<Subject>{
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Subject> findSubjects(SubjectInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(SubjectInfo info);
}