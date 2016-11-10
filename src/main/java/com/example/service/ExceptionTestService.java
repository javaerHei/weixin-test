package com.example.service;

import org.springframework.stereotype.Service;

import com.example.common.BusinessException;

@Service
public class ExceptionTestService {

	public void checkParams(int year) {
		if (year <= 0 || year > 120) {
			throw new BusinessException("参数错误");
		}
	}
}
