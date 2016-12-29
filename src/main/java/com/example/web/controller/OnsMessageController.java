package com.example.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.common.JsonUtils;
import com.example.ons.MessageSendService;

/**
 * ONS 消息发送Controller <br>
 * 创建日期：2016年12月23日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("/ons")
public class OnsMessageController extends BaseController {

	@Autowired
	private MessageSendService messageSendService;

	/**
	 * 普通消息发送demo
	 * 
	 * @since 1.0 <br>
	 *        <b>作者： @author gongmingguo</b> <br>
	 *        创建时间：2016年12月23日 上午10:19:06
	 */
	@RequestMapping("/send")
	@ResponseBody
	public String sendMessage(String message, @RequestParam(required = false, defaultValue = "1") Integer sendType) {
		logger.info("消息内容：{}，发送方式：{}", message, sendType);
		if (1 == sendType) {
			messageSendService.syncSend(message);
		}
		if (2 == sendType) {
			messageSendService.asyncSend(message);
		}
		if (3 == sendType) {
			messageSendService.onewaySend(message);
		}
		return JsonUtils.getSuccess();
	}
	
	/**
	 * 延迟消息发送demo
	 * 
	 * @since 1.0 <br>
	 *        <b>作者： @author gongmingguo</b> <br>
	 *        创建时间：2016年12月23日 上午10:19:06
	 */
	@RequestMapping("/send-delay")
	@ResponseBody
	public String sendMessage(String message, int delayTimeSeconds) {
		logger.info("消息内容：{}，延迟时间：{}秒", message, delayTimeSeconds);
		messageSendService.delaySend(message, delayTimeSeconds*1000);
		return JsonUtils.getSuccess();
	}
}
