package com.example.ons;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;

@Component
public class OnsListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private Consumer consumer;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// 防止重复执行
		if (event.getApplicationContext().getParent() == null) {
			System.out.println("subscribe ... ");
			consumer.subscribe("T_Jtest", "*", new MessageListener() {
				public Action consume(Message message, ConsumeContext context) {
					try {
						System.out.println(message);
						JSONObject result = JSONObject.parseObject(new String(message.getBody(), "UTF-8"));
						System.out.println("Receive: " + result);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					return Action.CommitMessage;
				}
			});
			consumer.start();
		}
	}

}
