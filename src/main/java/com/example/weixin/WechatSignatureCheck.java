package com.example.weixin;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 微信签名验证
 * <br>创建日期：2016年11月8日
 * <br><b>Copyright 2016 UTOUU All Rights Reserved</b>
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
public class WechatSignatureCheck {

	// 微信token
	private static final String WECHAT_TOKEN = "gmg";
	
	public static boolean checkToken(HttpServletRequest request) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		if(timestamp != null && nonce != null && signature != null ) {
			String[] paramsArray = {WECHAT_TOKEN,timestamp,nonce};
			Arrays.sort(paramsArray);
			StringBuilder paramsStr = new StringBuilder();
			for (String str : paramsArray) {
				paramsStr.append(str);
			}
			
			// sha1加密
			String enctyStr = DigestUtils.sha1Hex(paramsStr.toString());
			
			if(signature.equals(enctyStr)) {
				return true;
			}
		}
		return false;
		
	}
}
