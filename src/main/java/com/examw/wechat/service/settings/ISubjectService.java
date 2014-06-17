package com.examw.wechat.service.settings;

import com.examw.wechat.model.settings.ExamInfo;
import com.examw.wechat.model.settings.SubjectInfo;
import com.examw.wechat.service.IBaseDataService;

/**
 * 科目服务接口
 * @author fengwei.
 * @since 2014年4月29日 上午11:59:02.
 */
public interface ISubjectService extends IBaseDataService<SubjectInfo> {
	/**
	 * 根据科目ID获取考试信息。
	 * @param subjectId
	 * 科目ID。
	 * @return
	 * 考试信息。
	 */
	ExamInfo loadExam(String subjectId);
	/**
	 * 科目类型常量处理
	 * @param type
	 * @return
	 */
	String getTypeName(Integer type);
}