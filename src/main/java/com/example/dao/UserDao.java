package com.example.dao;

import com.example.bean.User;
import com.example.dto.UserDto;

public interface UserDao {

	UserDto findByName(String name);

	User getList();
	
}
