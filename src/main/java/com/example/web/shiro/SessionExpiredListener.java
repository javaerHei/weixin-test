package com.example.web.shiro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.session.events.SessionExpiredEvent;
import org.springframework.stereotype.Component;

// session失效事件监听器
@Component
public class SessionExpiredListener implements ApplicationListener<SessionExpiredEvent> {

	private static final Logger logger = LoggerFactory.getLogger(ShiroRealmImpl.class);
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	public void onApplicationEvent(SessionExpiredEvent event) {
		String sessionId = event.getSessionId();
		logger.info("session：{}失效", sessionId);
		// 发送sessio失效消息
		simpMessagingTemplate.convertAndSend("/queue/sessionDestory/" + sessionId, "session失效:" + sessionId);
	}


}
