package com.example;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringbootApplication.class)
@WebAppConfiguration
@ActiveProfiles("dev")
public class OnsApplicationTest {

	@Autowired
	private Producer producer;
	
	private static final String TPOIC_TEST = "T_Jtest";
	
	@Test
	public void redis_test1() throws UnsupportedEncodingException {
		Message message = new Message(TPOIC_TEST, "", "这个是测试消息".getBytes("UTF-8"));
		SendResult sendResult = producer.send(message);
		System.out.println(sendResult);
		try {
			Thread.sleep(25000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
}
