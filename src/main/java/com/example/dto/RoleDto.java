package com.example.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.bean.Permission;
import com.example.bean.Role;
import com.example.common.Collections3;

public class RoleDto extends Role {
	
	List<Permission> permissionList;

	public List<Permission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<Permission> permissionList) {
		this.permissionList = permissionList;
	}

	public List<String> getPermissionsName() {
		List<String> list = new ArrayList<String>();
		List<Permission> perlist = getPermissionList();
		if(Collections3.isEmpty(perlist)) {
			return null;
		}
		for (Permission per : perlist) {
			list.add(per.getPermissionName());
		}
		return list;
	}

}
