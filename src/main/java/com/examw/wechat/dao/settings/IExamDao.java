package com.examw.wechat.dao.settings;

import java.util.List;

import com.examw.wechat.dao.IBaseDao;
import com.examw.wechat.domain.settings.Exam;
import com.examw.wechat.model.settings.ExamInfo;
/**
 * 考试数据接口
 * @author fengwei.
 * @since 2014年4月29日 上午11:30:54.
 */
public interface IExamDao extends IBaseDao<Exam> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Exam> findExams(ExamInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(ExamInfo info);
}