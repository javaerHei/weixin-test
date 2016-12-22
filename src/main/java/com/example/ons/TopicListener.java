package com.example.ons;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;

/**
 * 消息监听器，Consumer注册消息监听器来订阅消息 
 * @author Administrator
 *
 */
@Component
public class TopicListener implements MessageListener {

	@Override
	public Action consume(Message message, ConsumeContext context) {
		try {
			System.out.println(message);
			String data = new String(message.getBody(), "UTF-8");
			System.out.println("Receive: " + data);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return Action.CommitMessage;
	}

}
