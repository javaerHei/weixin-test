package com.example.mns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.model.Message;

@Service
public class MnsMessageService {
	private static Logger logger = LoggerFactory.getLogger(MnsMessageService.class);

	@Autowired
	private CloudQueue testQueue;

	/**
	 * 
	 * @since 1.0
	 * @param messageContent
	 *            消息内容
	 * @param delaySeconds
	 *            设置消息延时，单位是秒 <br>
	 *            <b>作者： @author gongmingguo</b> <br>
	 *            创建时间：2016年12月26日 下午4:04:08
	 */
	private void sendMessage(String messageContent, int delaySeconds) {
		Message message = new Message();
		message.setMessageBody(messageContent);
		if (delaySeconds != 0) {
			message.setDelaySeconds(delaySeconds);
		}
		Message putMsg = testQueue.putMessage(message);
		logger.info("Send message id is: {}", putMsg.getMessageId());
	}

	public void send(String messageContent) {
		sendMessage(messageContent, 0);
	}

	public void delaySend(String messageContent, int delaySeconds) {
		sendMessage(messageContent, delaySeconds);
	}

	public void sendPriority(String messageContent) {
		for (int i = 1; i <= 50; i++) {
			Message message = new Message();
			if (i == 20) {
				message.setPriority(1);
			}
			message.setMessageBody("msg-" + i + ":" + messageContent);
			Message putMsg = testQueue.putMessage(message);
			logger.info("Send message id is: {}", putMsg.getMessageId());
		}
	}
}
