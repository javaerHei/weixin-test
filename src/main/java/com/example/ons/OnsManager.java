package com.example.ons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Consumer;

@Component
public class OnsManager implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private Consumer consumer;
	
	@Autowired
	private TopicListener topicListener;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// 防止重复执行
		if (event.getApplicationContext().getParent() == null) {
			System.out.println("subscribe ... ");
			consumer.subscribe("T_Jtest", "*", topicListener);
			consumer.start();
		}
	}

}
