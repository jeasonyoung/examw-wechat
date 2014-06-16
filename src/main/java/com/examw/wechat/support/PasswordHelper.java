package com.examw.wechat.support;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.StringUtils;

import com.examw.utils.AESUtil;
import com.examw.utils.HexUtil;
import com.examw.utils.MD5Util;
import com.examw.wechat.domain.security.User;
import com.examw.wechat.model.security.UserInfo;

/**
 * 密码AES加密。
 * @author yangyong.
 * @since 2014-05-09.
 */
public final class PasswordHelper {
	private String algorithmName = "md5";
	private int hashIterations = 2;
	/**
	 * 设置密码验证算法名称。
	 * @param algorithmName
	 */
	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}
	/**
	 * 设置迭代次数。
	 * @param hashIterations
	 */
	public void setHashIterations(int hashIterations) {
		this.hashIterations = hashIterations;
	}
	/**
	 * 创建AES对称加密密钥。
	 * @param account
	 * 用户账号。
	 * @return
	 * 密钥 md5(account+md5(account))。
	 */
	private static String createAESPasswordKey(String account){
		if(StringUtils.isEmpty(account)) return null;
		return MD5Util.MD5(account + MD5Util.MD5(account));
	}
	/**
	 * 加密验证密码。
	 * @param user
	 */
	public String encryptPassword(User user){
		if(user == null || StringUtils.isEmpty(user.getAccount())  || StringUtils.isEmpty(user.getPassword())) return null;
		String pwd = this.decryptAESPassword(user);
		
		return new SimpleHash(this.algorithmName, 
												 pwd, 
												 ByteSource.Util.bytes(user.getId()), 
												 hashIterations).toHex();
	}
	/**
	 * 加密用户密码。
	 * @param user
	 * 用户信息。
	 * @return
	 * 加密后的密码。
	 */
	public  String encryptAESPassword(UserInfo info){
		if(info == null || StringUtils.isEmpty(info.getAccount())  ||StringUtils.isEmpty(info.getPassword())) return null;
		String key = createAESPasswordKey(info.getAccount());
		byte[] encrypts = AESUtil.encrypt(info.getPassword(), key);
		if(encrypts == null || encrypts.length == 0)return null;
		return HexUtil.parseBytesHex(encrypts);
	}
	/**
	 * 解密用户密码。
	 * @param user
	 * 用户。
	 * @return 
	 *  解密后的密码。
	 */
	public  String decryptAESPassword(User user){
		if(user == null || StringUtils.isEmpty(user.getAccount())  || StringUtils.isEmpty(user.getPassword())) return null;
		
		byte[] encrypts = HexUtil.parseHexBytes(user.getPassword());
		if(encrypts == null || encrypts.length == 0) return null;
		String key = createAESPasswordKey(user.getAccount());
		if(StringUtils.isEmpty(key)) return null;
		return AESUtil.decrypt(encrypts, key);
	}
}