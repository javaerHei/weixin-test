package com.example.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.weixin.FormatXmlProcessUtils;
import com.example.weixin.WechatProcess;
import com.example.weixin.WechatSignatureCheck;
import com.example.weixin.WeiXinApiService;
import com.example.weixin.message.BaseMessage;

/**
 * 微信开发 <br>
 * 创建日期：2016年11月7日 <br>
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("/wechat")
public class WechatController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private WeiXinApiService weiXinApiService;

	@RequestMapping("")
	@ResponseBody
	public String wechat(HttpServletRequest request) throws IOException {

		String result = "";
		/** 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回 */
		String echostr = request.getParameter("echostr");
		if (echostr != null && echostr.length() > 0) {
			logger.info("开始校验微信签名");
			if (WechatSignatureCheck.checkToken(request)) {
				logger.info("校验微信签名：通过");
				return echostr;
			}
			logger.info("校验微信签名：未通过");

		} else {
			// 正常的微信处理流程
//			ReceiveXmlEntity xmlEntity = FormatXmlProcessUtils.getMsgEntity(xml);
			
			BaseMessage message = null;
			message = FormatXmlProcessUtils.getMessage(request.getInputStream());
			logger.info("用户OpenID：{}，消息类型：{}", message.getFromUserName(), message.getMsgType());

			String userInfo = weiXinApiService.getUserInfo(message.getFromUserName());
			logger.info("用户信息：{}", userInfo);
			
			result = new WechatProcess().processWechatMag(message);
		}
		return result;
	}

}
