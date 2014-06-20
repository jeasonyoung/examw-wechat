package com.examw.wechat.model.mgr;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;
import com.examw.wechat.support.CustomDateSerializer;

/**
 * 用户注册信息。
 * @author yangyong.
 * @since 2014-06-20.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RegisterInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,provinceId,provinceName,examId,examName,name,moblie,qq,ip;
	private Date createTime;
	/**
	 * 获取登记ID。
	 * @return 登记ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置登记ID。
	 * @param id
	 * 登记ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取所属省份ID。
	 * @return 所属省份ID。
	 */
	public String getProvinceId() {
		return provinceId;
	}
	/**
	 * 设置所属省份ID。
	 * @param provinceId
	 * 所属省份ID。
	 */
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	/**
	 * 获取所属省份名称。
	 * @return 所属省份名称。
	 */
	public String getProvinceName() {
		return provinceName;
	}
	/**
	 * 设置所属省份名称。
	 * @param provinceName
	 * 所属省份名称。
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	/**
	 * 获取考试ID。
	 * @return 考试ID。
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * 设置考试ID。
	 * @param examId
	 * 考试ID。
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * 获取考试名称。
	 * @return 考试名称。
	 */
	public String getExamName() {
		return examName;
	}
	/**
	 * 设置考试名称。
	 * @param examName
	 * 考试名称。
	 */
	public void setExamName(String examName) {
		this.examName = examName;
	}
	/**
	 * 获取真实姓名。
	 * @return 真实姓名。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置真实姓名。
	 * @param name
	 * 真实姓名。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取手机号码。
	 * @return 手机号码。
	 */
	public String getMoblie() {
		return moblie;
	}
	/**
	 * 设置手机号码。
	 * @param moblie
	 * 手机号码。
	 */
	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}
	/**
	 * 获取QQ号码。
	 * @return QQ号码。
	 */
	public String getQq() {
		return qq;
	}
	/**
	 * 设置QQ号码。
	 * @param qq
	 * QQ号码。
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}
	/**
	 * 获取登记IP。
	 * @return 登记IP。
	 */
	public String getIP() {
		return ip;
	}
	/**
	 * 设置登记IP。
	 * @param ip
	 * 登记IP。
	 */
	public void setIP(String ip) {
		this.ip = ip;
	}
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间。
	 * @param createTime
	 * 创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}