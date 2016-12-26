package com.example.mns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.Message;

/**
 * MNS的消息消费模式为拉（pull）, 消费端需要向队列循环拉取消息。
 * <br>创建日期：2016年12月26日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
@Component
public class MnsMessageListener implements Runnable, DisposableBean {
	private static Logger logger = LoggerFactory.getLogger(MnsMessageListener.class);
	private boolean running = true;

	@Autowired
	private CloudQueue testQueue;

	@Override
	public void run() {
		while (running) {
			
			// 当Queue消息量为空时，以PollingWaitSeconds 属性设置的时间长度执行一次。
			try {
				Message popMsg = testQueue.popMessage();
				if (popMsg != null) {
					//logger.info("message handle: {}", popMsg.getReceiptHandle());// 获取消息句柄
					logger.info("message body:{} ", popMsg.getMessageBodyAsString());
					//logger.info("message id:{} ", popMsg.getMessageId());
					//logger.info("message dequeue count: {}", popMsg.getDequeueCount());// 获取消息出队次数
					
					// 删除已经消费的消息
					testQueue.deleteMessage(popMsg.getReceiptHandle());
					logger.info("delete message successfully.\n");
					
				} else {
					logger.info("message not exist in TestQueue.\n");
				}
			} catch (ServiceException e) {
				e.printStackTrace();
			} catch (ClientException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void destroy() throws Exception {
		this.running = false;
		Thread.currentThread().interrupt();
		logger.info("mns消息监听器停止");
	}

}
