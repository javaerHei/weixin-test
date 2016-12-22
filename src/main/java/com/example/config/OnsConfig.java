package com.example.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

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

	@Bean
	public Producer producer() {
		Properties producerProperties = new Properties();
		producerProperties.put(PropertyKeyConst.ProducerId, producerId);
		producerProperties.put(PropertyKeyConst.AccessKey, accessKey);
		producerProperties.put(PropertyKeyConst.SecretKey, secretKey);
		
		Producer producer = ONSFactory.createProducer(producerProperties);
		producer.start();
		logger.info("........producer created successfully ........");
		return producer;
	}

	@Bean
	public Consumer consumer() {
		Properties consumerProperties = new Properties();
		consumerProperties.put(PropertyKeyConst.ConsumerId, consumerId);
		consumerProperties.put(PropertyKeyConst.AccessKey, accessKey);
		consumerProperties.put(PropertyKeyConst.SecretKey, secretKey);
		Consumer consumer = ONSFactory.createConsumer(consumerProperties);
		return consumer;
	}
}
