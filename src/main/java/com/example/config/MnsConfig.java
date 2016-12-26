package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.QueueMeta;

/**
 * mns <br>
 * 创建日期：2016年12月26日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
@Configuration
public class MnsConfig {

	private static Logger logger = LoggerFactory.getLogger(MnsConfig.class);

	@Value("${mns.accountEndpoint}")
	private String accountEndpoint;

	@Value("${mns.accessKeyId}")
	private String accessKeyId;

	@Value("${mns.accessKeySecret}")
	private String accessKeySecret;

	private static final String QUEUE_TEST = "TestQueue";

	@Bean
	public MNSClient mnsClient() {
		CloudAccount account = new CloudAccount(accessKeyId, accessKeySecret, accountEndpoint);
		MNSClient client = account.getMNSClient(); // 在程序中，CloudAccount以及MNSClient单例实现即可，多线程安全
		return client;
	}

	@Bean
	public CloudQueue testQueue() {
		/*PagingListResult<QueueMeta> listQueue = this.mnsClient().listQueue(QUEUE_TEST, "", 1000);
		if (listQueue != null) {
			List<QueueMeta> result = listQueue.getResult();
			List<String> queueNames = new ArrayList<String>();
			if (CollectionUtils.isNotEmpty(result)) {
				for (QueueMeta queueMeta : result) {
					queueNames.add(queueMeta.getQueueName());
				}
				if (queueNames.contains(QUEUE_TEST)) {
					return this.mnsClient().getQueueRef(QUEUE_TEST);
				}
			}
		}*/

		QueueMeta meta = new QueueMeta(); // 生成本地QueueMeta属性，有关队列属性详细介绍见https://help.aliyun.com/document_detail/27476.html
		meta.setQueueName(QUEUE_TEST); // 设置队列名
		meta.setPollingWaitSeconds(15);// 长轮询,当Queue消息量为空时，针对该 Queue 的 ReceiveMessage 请求最长的等待时间，单位为秒。0-30秒范围内的某个整数值
		meta.setMaxMessageSize(2048L);// 此属性指定MNS队列中的消息可以包含的字节数限值。可以设置为1024字节(1KB) 到最大65536 字节 (64KB)之间的任何值。
		CloudQueue queue = null;

		try {
			queue = this.mnsClient().createQueue(meta);
			System.out.println("Create queue successfully. URL: " + queue.getQueueURL());
		} catch (ClientException ce) {
			System.out.println("Something wrong with the network connection between client and MNS service."
					+ "Please check your network and DNS availablity.");
			ce.printStackTrace();
		} catch (ServiceException se) {
			se.printStackTrace();
			logger.error("MNS exception requestId:" + se.getRequestId(), se);
			if (se.getErrorCode() != null) {
				if (se.getErrorCode().equals("QueueNotExist")) {
					System.out.println("Queue is not exist.Please create before use");
				} else if (se.getErrorCode().equals("TimeExpired")) {
					System.out.println("The request is time expired. Please check your local machine timeclock");
				}
				/*
				 * you can get more MNS service error code from following link:
				 * https://help.aliyun.com/document_detail/mns/api_reference/
				 * error_code/error_code.html
				 */
			}
		} catch (Exception e) {
			System.out.println("Unknown exception happened!");
			e.printStackTrace();
		}
		return queue;
	}

}
