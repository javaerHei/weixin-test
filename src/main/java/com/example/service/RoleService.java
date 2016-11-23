package com.example.service;

import java.util.List;

import com.example.dto.RoleDto;

public interface RoleService {

	List<RoleDto> getUserRoles(Long userId);
	
}
