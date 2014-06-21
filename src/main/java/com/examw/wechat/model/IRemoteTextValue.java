package com.examw.wechat.model;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 远程数据传递接口。
 * @author yangyong.
 * @since 2014-06-21.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public interface IRemoteTextValue extends Serializable {
	/**
	 * 获取数据ID。
	 * @return 数据ID。
	 */
	String getId();
	/**
	 * 获取数据显示名称。
	 * @return
	 * 数据显示名称。
	 */
	String getName();
}