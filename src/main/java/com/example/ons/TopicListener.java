package com.example.ons;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;

/**
 *
 *ons 消息监听
 */
@Component
public class TopicListener implements MessageListener {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private long lastReconsumeTime = 0;
	
	@Override
	public Action consume(Message message, ConsumeContext context) {
		try {
			doConsumeMessage(message);
		} catch (Throwable e) {
			e.printStackTrace();
			// 消息消费失败后期望消息重试,集群消费方式下，消息消费失败后期望消息重试，需要在消息监听器接口的实现中明确进行配置（三种方式任选一种）：
			// 1.返回 Action.ReconsumeLater （推荐） 2.返回 Null 3.抛出异常
			return Action.ReconsumeLater;
		}
		// 消费成功，继续消费下一条消息
		return Action.CommitMessage;
	}

	public void doConsumeMessage(Message message) throws UnsupportedEncodingException {
		
			String data = new String(message.getBody(), "UTF-8");
			logger.info("收到的消息：{}", data);
			// 获取消息的重试次数
			int reconsumeTimes = message.getReconsumeTimes();
			if (reconsumeTimes > 0) {
				logger.info("消息的重试次数:{},距离上次消费时间：{}秒", message.getReconsumeTimes(),(System.currentTimeMillis() - lastReconsumeTime)/1000);
			}
			lastReconsumeTime = System.currentTimeMillis();
		
		//throw new BusinessException("消费异常");
	}
}