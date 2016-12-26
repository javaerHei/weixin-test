package com.example.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.common.JsonUtils;
import com.example.mns.MnsMessageService;

/**
 * MNS 消息发送Controller <br>
 * 创建日期：2016年12月23日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("/mns")
public class MnsMessageController extends BaseController {

	@Autowired
	private MnsMessageService messageService;

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
		if(1 == sendType) {
			messageService.send(message);
		}else if(2 == sendType) {
			messageService.sendPriority(message);
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
	public String sendMessageDelay(String message, int delayTimeSeconds) {
		logger.info("消息内容：{}，延迟时间：{}秒", message, delayTimeSeconds);
		messageService.delaySend(message, delayTimeSeconds);
		return JsonUtils.getSuccess();
	}
}
