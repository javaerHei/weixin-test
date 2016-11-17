package com.example.dao;

import java.util.List;

import com.example.dto.RoleDto;

public interface RoleDao {

	List<RoleDto> getUserRoles(Long userId);
}
