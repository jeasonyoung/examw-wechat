package com.examw.wechat.service.mgr;

import com.examw.wechat.message.Context;
import com.examw.wechat.model.mgr.RecordInfo;
import com.examw.wechat.service.IBaseDataService;

/**
 * 消息记录服务。
 * @author yangyong.
 * @since 2014-07-02.
 */
public interface IRecordService extends IBaseDataService<RecordInfo> {
	/**
	 * 添加消息。
	 * @param context
	 * 上下文。
	 * @param content
	 * 请求内容。
	 */
	void saveRecord(Context context,String content);
}