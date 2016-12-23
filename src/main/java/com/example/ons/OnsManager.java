package com.example.ons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Consumer;

/**
 * 代码里涉及到的 Topic， Producer ID， Consumer ID，需要到 MQ 控制台上创建
 * <br>创建日期：2016年12月23日
 * 
 * @author gongmingguo
 * @since 1.0
 * @version 1.0
 */
@Component
public class OnsManager implements ApplicationListener<ContextRefreshedEvent> {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private Consumer consumer;
	
	@Autowired
	private TopicListener topicListener;
	
	@Value("${ons.listenerEnabled}")
	private boolean listenerEnabled;
	

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// 防止重复执行
		if (event.getApplicationContext().getParent() == null) {
			if(listenerEnabled) {
				logger.info("消息监听器开始。。。");
				consumer.subscribe("T_Jtest", "*", topicListener);
				consumer.start();
			}
		}
	}

}
