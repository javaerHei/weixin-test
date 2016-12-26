package com.example.mns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression("${mns.listenerEnabled}")
public class StartUpListener implements ApplicationListener<ContextRefreshedEvent> {

	private static Logger logger = LoggerFactory.getLogger(MnsMessageService.class);
	
	@Autowired
	private MnsMessageListener mnsMessageListener;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Thread listener = new Thread(mnsMessageListener);
		listener.start();
		logger.info("启动mns消息监听成功！");
	}

}
