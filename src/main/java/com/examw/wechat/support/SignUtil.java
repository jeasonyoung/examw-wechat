package com.examw.wechat.support;
import java.util.Arrays;

import org.springframework.util.StringUtils;

import com.examw.utils.MD5Util;

/**
 * 校验工具类。
 * @author yangyong.
 * @since 2014-02-21.
 * */
public final class SignUtil {
	/**
	 * 验证签名。
	 * @param signature
	 * 	加密签名。
	 * @param token
	 * 	令牌。
	 * @param timestamp
	 *  时间戳
	 * @param nonce
	 * 	随机数。
	 * @return 验证通过返回true,否则返回false。
	 * */
	public synchronized static boolean checkSignature(String signature,String token,String timestamp,String nonce){
		//将token,timestamp,nonce三个参数进行字典排序。
		String[] source = new String[]{token,timestamp,nonce};
		Arrays.sort(source);
		StringBuilder content = new StringBuilder();
		for(int i = 0; i < source.length; i++){
			content.append(source[i]);
		}
		String result = MD5Util.MD5(content.toString());
		return (StringUtils.isEmpty(result) || StringUtils.isEmpty(signature)) ? false : result.equalsIgnoreCase(signature);
	}
}