package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.UserDao;
import com.example.dto.RoleDto;
import com.example.dto.UserDto;
import com.example.service.RoleService;
import com.example.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleService roleService;
	
	@Override
	public UserDto findByName(String name) {
		UserDto userDto = userDao.findByName(name);
		if(userDto != null) {
			// 查询用户角色
			Long userId = userDto.getId();
			List<RoleDto> userRoles = roleService.getUserRoles(userId);
			userDto.setRoleList(userRoles);
			// 查询权限
		}
		return userDto;
	}

}
