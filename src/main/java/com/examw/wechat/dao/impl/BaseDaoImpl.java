package com.examw.wechat.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.examw.wechat.dao.IBaseDao;
/**
 * 数据操作接口实现类。
 * @author yangyong.
 * @since 2014-04-28.
 */
public class BaseDaoImpl<T> implements IBaseDao<T> {
	private SessionFactory sessionFactory;
	/**
	 * 设置SessionFactory。
	 * @param sessionFactory
	 * 	Hibernate Session 对象。
	 * */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	/**
	 * 获取当前会话。
	 * @return 当前会话。
	 * */
	protected Session getCurrentSession(){
		return this.sessionFactory == null ? null : this.sessionFactory.getCurrentSession();
	}
	/**
	 * 加载指定主键对象。
	 * @param c
	 * 	对象类型。
	 * @param id
	 * 	主键值。
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public T load(Class<T> c, Serializable id) {
		if(c != null && id != null){
			return (T)this.getCurrentSession().get(c, id);
		}
		return null;
	}
	/**
	 * 保存新增对象。
	 * @param data
	 * 	对象。
	 * */
	@Override
	public Serializable save(T data) {
		if(data != null){
			return this.getCurrentSession().save(data);
		}
		return null;
	}
	/**
	 * 更新对象。
	 * @param data
	 * 	对象。
	 * */
	@Override
	public void update(T data) {
		if(data != null){
			this.getCurrentSession().update(data);
		}
	}
	/**
	 * 保存或更新对象。
	 * @param data
	 * 	对象。
	 * */
	@Override
	public void saveOrUpdate(T data) {
		if(data != null) this.getCurrentSession().saveOrUpdate(data);
	}
	/**
	 * 删除对象。
	 * @param data
	 * 	对象。
	 * */
	@Override
	public void delete(T data) {
		if(data != null){
			this.getCurrentSession().delete(data);
		}
	}
	/**
	 * 查找对象集合。
	 * @param hql
	 * 	HQL语句。
	 * @param parameters
	 * 	参数集合。
	 * @param page
	 * 	页码。
	 * @param rows
	 * 	页数据量
	 * <pre>
	 * 	当page与rows同时为null时，则查询全部数据。
	 * </pre>
	 * @return 结果数据集合。
	 * */
	@SuppressWarnings("unchecked") 
	protected List<T> find(String hql, Map<String, Object> parameters,Integer page, Integer rows) {
		if(hql == null || hql.isEmpty()) return null;
		Query query = this.getCurrentSession().createQuery(hql);
		if(query != null){
			this.addParameters(query, parameters);
			if((page == null) && (rows == null))return query.list();
			if((page == null) || (page < 1)) page = 1;
			if((rows == null) || (rows < 10)) rows = 10;
			return query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		}
		return null;
	}
	/**
	 * 添加参数集合。
	 * @param query
	 * @param parameters
	 */
	@SuppressWarnings("rawtypes")
	protected void addParameters(Query query, Map<String, Object> parameters){
		if(query == null)return;
		if(parameters == null || parameters.size() == 0) return;
		Object  value = null;
		for(Map.Entry<String, Object> entry : parameters.entrySet()){
			 value = entry.getValue();
			 if(value != null && value.getClass().isArray()){
				query.setParameterList(entry.getKey(), (Object[])value);
			 }else if (value != null && (value instanceof Collection)) {
				query.setParameterList(entry.getKey(), (Collection)value);
			}else {
				query.setParameter(entry.getKey(), value);
			}
		}
		//是否启用缓存。
		query.setCacheable(true);
	}
	/**
	 * 统计数据总数。
	 * @param hql
	 *  HQL语句。
	 * @param parameters
	 * 	参数集合。
	 * @return 数据总数。
	 * */
	protected Long count(String hql, Map<String, Object> parameters) {
		if(hql == null || hql.isEmpty()) return null;
		Query query = this.getCurrentSession().createQuery(hql);
		if(query != null){
			this.addParameters(query, parameters);
			return (Long)query.uniqueResult();
		}
		return null;
	}
}