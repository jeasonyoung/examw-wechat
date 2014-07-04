package com.examw.wechat.test;

import java.io.UnsupportedEncodingException;

import org.junit.Test;


public class TestMd5 {
	@Test
	public void test(){
		String [] sources = new String[]{"【招警备考公安学基础理论第七讲第一节】",
														   "【招警备考公安学基础理论第七讲第二节】",
														   "【招警备考公安学基础理论第七讲第三节】"};
		for(int i = 0; i < sources.length; i++){
			System.out.println(String.format("%1$s.  %2$s = %3$s", i+1, sources[i], this.md5String(sources[i])));
		}
	}
	
	private String md5String(String value){
		try {
			return org.springframework.util.DigestUtils.md5DigestAsHex(value.getBytes("utf8"));
		} catch (UnsupportedEncodingException e) { 
			e.printStackTrace();
		}
		return "";
	}
}