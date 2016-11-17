package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.RoleDao;
import com.example.dto.RoleDto;
import com.example.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public List<RoleDto> getUserRoles(Long userId) {
		return roleDao.getUserRoles(userId);
	}

}
