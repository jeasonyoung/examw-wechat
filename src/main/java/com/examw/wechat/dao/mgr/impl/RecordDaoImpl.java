package com.examw.wechat.dao.mgr.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.wechat.dao.impl.BaseDaoImpl;
import com.examw.wechat.dao.mgr.IRecordDao;
import com.examw.wechat.domain.mgr.Record;
import com.examw.wechat.model.mgr.RecordInfo;

/**
 * 消息记录数据实现。
 * @author yangyong.
 * @since 2014-07-02.
 */
public class RecordDaoImpl extends BaseDaoImpl<Record> implements IRecordDao {
	/*
	 * 查询。
	 * @see com.examw.wechat.dao.mgr.IRecordDao#findRecords(com.examw.wechat.model.mgr.RecordInfo)
	 */
	@Override
	public List<Record> findRecords(RecordInfo info) {
		String hql = "from Record r where  1 = 1  ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by r." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询统计。
	 * @see com.examw.wechat.dao.mgr.IRecordDao#total(com.examw.wechat.model.mgr.RecordInfo)
	 */
	@Override
	public Long total(RecordInfo info) {
		String hql = "select count(*) from Record r where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//查询条件。
	private String addWhere(RecordInfo info, String hql,Map<String, Object> parameters){
		//公众号ID。
		if(!StringUtils.isEmpty(info.getAccountId())){
			hql += " r.accountUser.account.id = :accountId";
			parameters.put("accountId", info.getAccountId());
		}
		//关注用户OpenID。
		if(!StringUtils.isEmpty(info.getOpenId())){
			hql += " r.accountUser.openId = :openId";
			parameters.put("openId", info.getOpenId());
		}
		//关注用户名称。
		if(!StringUtils.isEmpty(info.getUserName())){
			hql += " r.accountUser.register.name like :name";
			parameters.put("name", "%"+ info.getUserName() +"%");
		}
		return hql;
	}
}