/*package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.jms.core.JmsTemplate;

import com.example.jms.Msg;

@SpringBootApplication
public class JmsApplication implements CommandLineRunner {

	@Autowired
	private JmsTemplate jmsTemplate;

	public static void main(String[] args) {
		// SpringApplication.run(SpringbootApplication.class, args);
		SpringApplication application = new SpringApplication(JmsApplication.class);
		application.setBannerMode(Banner.Mode.OFF);//
		application.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		jmsTemplate.send("my-destination", new Msg());
		
	}
}*/
