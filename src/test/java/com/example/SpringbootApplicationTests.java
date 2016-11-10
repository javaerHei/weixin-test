package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.example.bean.Person;
import com.example.dao.PersonDao;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringbootApplication.class)
@WebAppConfiguration
@ActiveProfiles("dev")
public class SpringbootApplicationTests {

	@Autowired
	private PersonDao personDao;
	
	@Autowired
	RedisTemplate<Object,Object> redisTemplate;
	
	@Test
	public void redis_test1() {
		//personDao.stringRedisTemplateDemo();
		
		Person person = new Person();
		person.setId("1");
		person.setName("鬼门关");
		person.setAge(12);
		personDao.save(person);
	}

	@Test
	public void redis_test2() {
		
		Person person = new Person();
		person.setId("1");
		person.setName("鬼门关");
		person.setAge(12);
		
		ValueOperations<Object,Object> valueOperations = redisTemplate.opsForValue();
		valueOperations.set("2", person);
	}
	
	@Test
	public void redis_test3() {
		RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection();
		//redisConnection.set("asd".getBytes() ,"value2".getBytes());
		
		Long lLen = redisConnection.lLen("list1".getBytes());
		System.out.println(lLen);
	}
}
