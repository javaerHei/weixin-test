package com.example.weixin;

import com.example.weixin.event.MenuEvent;
import com.example.weixin.message.BaseMessage;
import com.example.weixin.message.TextMessage;
import com.example.weixin.message.VoiceMessage;

/**
 * 微信xml消息处理流程逻辑类
 * 
 */
public class WechatProcess {

	/**
	 * 解析处理xml、获取智能回复结果（通过图灵机器人api接口）
	 * 
	 * @param xml
	 *            接收到的微信数据
	 * @return 最终的解析结果（xml格式数据）
	 */
	public String processWechatMag(BaseMessage message) {
		String result = "";

		if (message == null) {
			return null;
		}

		/** 以文本消息为例，调用图灵机器人api接口，获取回复内容 */
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			result = new TulingApiProcess().getTulingResult(textMessage.getContent());
			/**
			 * 此时，如果用户输入的是“你好”，在经过上面的过程之后，result为“你也好”类似的内容
			 * 因为最终回复给微信的也是xml格式的数据，所有需要将其封装为文本类型返回消息
			 */
			result = FormatXmlProcessUtils.formatTextMessage(message.getFromUserName(), message.getToUserName(),
					result);
		} else if (message instanceof VoiceMessage) {
			VoiceMessage textMessage = (VoiceMessage) message;
			result = FormatXmlProcessUtils.formatTextMessage(message.getFromUserName(), message.getToUserName(),
					textMessage.getRecognition());
		} else if (message instanceof MenuEvent) {
			MenuEvent event = (MenuEvent) message;
			String eventKey = event.getEventKey();
			if("V1001_GOOD".equals(eventKey)) {
				// 
				System.out.println("用户:"+event.getFromUserName() + " 点赞我们!");
				result = FormatXmlProcessUtils.formatTextMessage(message.getFromUserName(), message.getToUserName(),
						"感谢您的点赞");
			}
			System.out.println(eventKey);
		}
		System.out.println(result);
		return result;
	}

}
