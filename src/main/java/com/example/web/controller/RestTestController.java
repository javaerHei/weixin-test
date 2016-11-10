package com.example.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.example.service.ExceptionTestService;

@RestController
public class RestTestController {

	@Autowired
	private ExceptionTestService exceptionTestService;
	
	@RequestMapping("/json")
	public String getJsonResult(int year) {
		exceptionTestService.checkParams(year);
		JSONObject result = new JSONObject();
		result.put("success", true);
		result.put("data", 12);
		return result.toJSONString();
	}
}
