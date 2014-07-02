package com.examw.wechat.service.mgr.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.wechat.dao.mgr.IAccountUserDao;
import com.examw.wechat.dao.mgr.IRecordDao;
import com.examw.wechat.domain.mgr.AccountUser;
import com.examw.wechat.domain.mgr.Record;
import com.examw.wechat.message.Context;
import com.examw.wechat.model.mgr.RecordInfo;
import com.examw.wechat.service.impl.BaseDataServiceImpl;
import com.examw.wechat.service.mgr.IRecordService;

/**
 * 消息记录服务实现。
 * @author yangyong.
 * @since 2014-07-02.
 */
public class RecordServiceImpl extends BaseDataServiceImpl<Record, RecordInfo> implements IRecordService {
	private static final Logger logger = Logger.getLogger(RecordServiceImpl.class);
	private IRecordDao recordDao;
	private IAccountUserDao accountUserDao; 
	/**
	 * 设置消息记录数据接口。
	 * @param recordDao
	 * 消息记录数据接口。
	 */
	public void setRecordDao(IRecordDao recordDao) {
		this.recordDao = recordDao;
	}
	/**
	 * 设置关注用户数据接口。
	 * @param accountUserDao
	 */
	public void setAccountUserDao(IAccountUserDao accountUserDao) {
		this.accountUserDao = accountUserDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Record> find(RecordInfo info) {
		return this.recordDao.findRecords(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected RecordInfo changeModel(Record data) {
		if(data == null) return null;
		RecordInfo info = new RecordInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getAccountUser() != null){
			if(data.getAccountUser().getAccount() != null){
				info.setAccountId(data.getAccountUser().getAccount().getId());
				info.setAccountName(data.getAccountUser().getAccount().getName());
			}
			info.setOpenId(data.getAccountUser().getOpenId());
			if(data.getAccountUser().getRegister() != null){
				info.setUserName(data.getAccountUser().getRegister().getName());
			}
		}
		return info;
	}
	/*
	 * 查询统计。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(RecordInfo info) {
		return this.recordDao.total(info);
	}
	/*
	 * 更新。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public RecordInfo update(RecordInfo info) {
		String error = "暂不支持更新！";
		logger.error(error);
		throw new RuntimeException(error);
	}
	/*
	 * 删除。
	 * @see com.examw.wechat.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			Record data = this.recordDao.load(Record.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled())logger.debug("删除消息：" + ids[i]);
				this.recordDao.delete(data);
			}
		}
	}
	/*
	 * 添加消息记录。
	 * @see com.examw.wechat.service.mgr.IRecordService#saveRecord(com.examw.wechat.message.Context)
	 */
	@Override
	public void saveRecord(Context context,String content) {
		 if(logger.isDebugEnabled()) logger.debug("通过消息上下文添加消息记录...");
		 if(context == null) return;
		 AccountUser accountUser = this.accountUserDao.loadUser(context.getAccountId(), context.getOpenId());
		 if(accountUser == null){
			 if(logger.isDebugEnabled()) logger.debug("［公众号："+context.getAccountId()+"，下微信用户OpenId:"+context.getOpenId()+"］不是注册的关注用户！");
			 return;
		 }
		 Record data = new Record();
		 data.setId(UUID.randomUUID().toString());
		 data.setCreateTime(new Date());
		 data.setAccountUser(accountUser);
		 data.setContent(content);
		 this.recordDao.save(data);
	}
}