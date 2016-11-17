package com.example.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.bean.Role;
import com.example.bean.User;
import com.example.common.Collections3;

public class UserDto extends User {

	private List<RoleDto> roleList;

	public List<RoleDto> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleDto> roleList) {
		this.roleList = roleList;
	}

	public Set<String> getRolesName() {
		List<RoleDto> roles = getRoleList();
		if(Collections3.isEmpty(roles)) {
			return null;
		}
		Set<String> set = new HashSet<String>();
		
		for (Role role : roles) {
			set.add(role.getRoleName());
		}
		return set;
	}
}
