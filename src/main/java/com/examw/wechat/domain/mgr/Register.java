package com.examw.wechat.domain.mgr;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.examw.wechat.domain.settings.Exam;
import com.examw.wechat.domain.settings.Province;

/**
 * 登记信息。
 * @author yangyong.
 * @since 2014-06-20.
 */
public class Register implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,moblie,qq,ip;
	private Province province;
	private Exam exam;
	private Date createTime;
	private Set<AccountUser> users;
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
	 * 获取所在省份。
	 * @return 所在省份。
	 */
	public Province getProvince() {
		return province;
	}
	/**
	 * 设置所在省份。
	 * @param province
	 * 所在省份。
	 */
	public void setProvince(Province province) {
		this.province = province;
	}
	/**
	 * 设置所属考试。
	 * @return 所属考试。
	 */
	public Exam getExam() {
		return exam;
	}
	/**
	 * 设置所属考试。
	 * @param exam
	 * 所属考试。
	 */
	public void setExam(Exam exam) {
		this.exam = exam;
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
	/**
	 * 获取关联的微信用户集合。
	 * @return 关联的微信用户集合。
	 */
	public Set<AccountUser> getUsers() {
		return users;
	}
	/**
	 * 设置关联的微信用户集合。
	 * @param users
	 * 关联的微信用户集合。
	 */
	public void setUsers(Set<AccountUser> users) {
		this.users = users;
	}
}