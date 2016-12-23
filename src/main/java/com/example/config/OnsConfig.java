package com.example.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.example.ons.TopicListener;

@Configuration
public class OnsConfig {
	private static Logger logger = LoggerFactory.getLogger(OnsConfig.class);

	@Value("${ons.AccessKey}")
	private String accessKey;

	@Value("${ons.SecretKey}")
	private String secretKey;

	@Value("${ons.ProducerId}")
	private String producerId;

	@Value("${ons.ConsumerId}")
	private String consumerId;

	@Autowired
	private TopicListener topicListener;

	private static final String TPOIC_TEST = "T_Jtest";

	@Bean(initMethod = "start", destroyMethod = "shutdown")
	public ProducerBean onsProducer() {
		Properties producerProperties = new Properties();
		producerProperties.put(PropertyKeyConst.ProducerId, producerId);
		producerProperties.put(PropertyKeyConst.AccessKey, accessKey);
		producerProperties.put(PropertyKeyConst.SecretKey, secretKey);

		ProducerBean producerBean = new ProducerBean();
		producerBean.setProperties(producerProperties);
		logger.info("........producer created successfully ........");
		return producerBean;
	}

	@Bean(initMethod = "start", destroyMethod = "shutdown")
	@ConditionalOnExpression("${ons.listenerEnabled}")
	public ConsumerBean onsConsumer() {

		Properties consumerProperties = new Properties();
		consumerProperties.put(PropertyKeyConst.ConsumerId, consumerId);
		consumerProperties.put(PropertyKeyConst.AccessKey, accessKey);
		consumerProperties.put(PropertyKeyConst.SecretKey, secretKey);

		ConsumerBean consumerBean = new ConsumerBean();
		consumerBean.setProperties(consumerProperties);

		Map<Subscription, MessageListener> subscriptionTable = new HashMap<>();

		Subscription subscription = new Subscription();
		subscription.setTopic(TPOIC_TEST);
		// expression即Tag，可以设置成具体的Tag，如 taga||tagb||tagc，也可设置成*。
		// *仅代表订阅所有Tag，不支持通配
		subscription.setExpression("*");
		subscriptionTable.put(subscription, topicListener);

		consumerBean.setSubscriptionTable(subscriptionTable);
		logger.info("ons消息监听器初始化成功...");
		return consumerBean;
	}
}
