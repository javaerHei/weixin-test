package com.example.ons;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;

/**
 * ons消息发送 <br>
 * 创建日期：2016年12月23日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
@Service
public class MessageSendService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Producer producer;

	private static final String TPOIC_TEST = "T_Jtest";

	/**
	 * 同步发送
	 * 
	 * @throws UnsupportedEncodingException
	 * @since 1.0 <br>
	 *        <b>作者： @author gongmingguo</b> <br>
	 *        创建时间：2016年12月23日 上午10:08:08
	 */
	public void syncSend(String messageContent) {
		Message message = null;
		try {
			message = new Message(TPOIC_TEST, // MQ消息的Topic，需要事先申请
					"", // MQ Tag，可以进行消息过滤
					messageContent.getBytes("UTF-8"));// 消息体，和MQTT的body对应
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		// 同步发送消息，只要不抛异常就是成功
		SendResult sendResult = producer.send(message);
		System.out.println(sendResult);
	}

	/**
	 * 异步发送
	 * 
	 * @throws UnsupportedEncodingException
	 * @since 1.0 <br>
	 *        <b>作者： @author gongmingguo</b> <br>
	 *        创建时间：2016年12月23日 上午10:11:33
	 */
	public void asyncSend(String messageContent) {
		Message message = null;
		try {
			message = new Message(TPOIC_TEST, // MQ消息的Topic，需要事先申请
					"", // MQ Tag，可以进行消息过滤
					messageContent.getBytes("UTF-8"));// 消息体，和MQTT的body对应
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		// 异步发送消息, 发送结果通过callback返回给客户端。
		producer.sendAsync(message, new SendCallback() {

			@Override
			public void onSuccess(SendResult sendResult) {
				// 消息发送成功
				logger.info("消息发送成功. topic={},msgId={}", sendResult.getTopic(), sendResult.getMessageId());
			}

			@Override
			public void onException(OnExceptionContext context) {
				// 消息发送失败
				logger.info("消息发送失败. topic={},msgId={},异常:{}", context.getTopic(), context.getMessageId(),
						context.getException().getMessage());
			}
		});

		// 在callback返回之前即可取得msgId。
		logger.info("异步发送消息. topic={},msgId={}", message.getTopic(), message.getMsgID());
	}

	/**
	 * 单向(Oneway)发送
	 * 
	 * @throws UnsupportedEncodingException
	 * @since 1.0 <br>
	 *        <b>作者： @author gongmingguo</b> <br>
	 *        创建时间：2016年12月23日 上午10:08:08
	 */
	public void onewaySend(String messageContent) {
		Message message = null;
		try {
			message = new Message(TPOIC_TEST, // MQ消息的Topic，需要事先申请
					"", // MQ Tag，可以进行消息过滤
					messageContent.getBytes("UTF-8"));// 消息体，和MQTT的body对应
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		// oneway发送消息，只要不抛异常就是成功
		producer.sendOneway(message);
	}

	/**
	 * 延时消息用于指定消息发送到MQ服务器端后，延时一段时间才被投递到客户端进行消费（例如3秒后才被消费），
	 * 适用于解决一些消息生产和消费有时间窗口要求的场景，或者通过消息触发延迟任务的场景，类似于延迟队列。
	 * @since 1.0
	 * @param messageContent
	 * @param delayTimeMillis (毫秒)
	 *            <br>
	 *            <b>作者： @author gongmingguo</b> <br>
	 *            创建时间：2016年12月23日 下午12:14:03
	 */
	public void delaySend(String messageContent, long delayTimeMillis) {
		Message message = null;
		try {
			message = new Message(TPOIC_TEST, // MQ消息的Topic，需要事先申请
					"", // MQ Tag，可以进行消息过滤
					messageContent.getBytes("UTF-8")); // 消息体，和MQTT的body对应
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		message.setStartDeliverTime(System.currentTimeMillis() + delayTimeMillis);
		// 同步发送消息，只要不抛异常就是成功
		SendResult sendResult = producer.send(message);
		System.out.println(sendResult);
	}
}
