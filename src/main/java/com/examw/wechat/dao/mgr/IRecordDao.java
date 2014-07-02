package com.examw.wechat.dao.mgr;

import java.util.List;

import com.examw.wechat.dao.IBaseDao;
import com.examw.wechat.domain.mgr.Record;
import com.examw.wechat.model.mgr.RecordInfo;

/**
 * 消息记录数据接口。
 * @author yangyong.
 * @since 2014-07-02
 */
public interface IRecordDao extends IBaseDao<Record> {
	/**
	 * 查询消息记录。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果集合。
	 */
	List<Record> findRecords(RecordInfo info);
	/**
	 * 查询统计。
	 * @param info
	 * 查询条件。
	 * @return
	 */
	Long total(RecordInfo info);
}