package com.examw.wechat.service.settings;

import java.util.List;

import com.examw.model.TreeNode;
import com.examw.wechat.model.settings.CatalogInfo;
import com.examw.wechat.model.settings.ExamInfo;
import com.examw.wechat.service.IBaseDataService;

/**
 * 考试服务接口
 * @author fengwei.
 * @since 2014年4月29日 上午11:58:16.
 */
public interface IExamService extends IBaseDataService<ExamInfo> {
	/**
	 * 根据考试设置ID获取考试类别信息。
	 * @param examId
	 * 考试设置ID
	 * @return
	 * 考试类别信息。
	 */
	CatalogInfo loadCatalog(String examId);
	/**
	 * 加载类型考试。
	 * @return
	 */
	List<TreeNode> loadCatalogExams();
}