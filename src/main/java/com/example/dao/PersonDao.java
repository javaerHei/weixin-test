package com.example.dao;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.example.bean.Person;

@Repository
public class PersonDao {

//	@Autowired
	//private StringRedisTemplate stringRedisTemplate;

	@Resource(name = "stringRedisTemplate")
	ValueOperations<Object, Object> valOpsStr;

	@Autowired
	RedisTemplate<Object, Object> redisTemplate;
	

	@Resource(name = "redisTemplate")
	ValueOperations<Object, Object> valueOps;
	
	public void stringRedisTemplateDemo() {
		valOpsStr.set("xx", "yy");
	}
	
	public void save(Person person) {
		valueOps.set(person.getId(), person);
	}
	
	public String getString() {
		return (String) valOpsStr.get("xx");
	}
	
	public Person getPerson() {
		return (Person) valueOps.get("1");
	}
}
