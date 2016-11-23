package com.example.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.example.bean.Permission;
import com.example.common.Collections3;
import com.example.dao.RoleDao;
import com.example.dto.RoleDto;

@Repository
public class RoleDaoImpl extends BaseDaoImpl implements RoleDao {

	@Override
	public List<RoleDto> getUserRoles(Long userId) {
		List<RoleDto> roleList = jdbcTemplate.query(
				"select r.id,r.role_name from t_user_role ur left join t_role r on r.id = ur.role_id where ur.user_id = ?",
				new Object[] { userId }, BeanPropertyRowMapper.newInstance(RoleDto.class));
		if(!Collections3.isEmpty(roleList)) {
			for (RoleDto roleDto : roleList) {
				Long roleId = roleDto.getId();
				List<Permission> permissionList = jdbcTemplate.query(
						"select id,permission_name from t_permission where role_id = ?", new Object[] { roleId },
						BeanPropertyRowMapper.newInstance(Permission.class));
				roleDto.setPermissionList(permissionList);
			}
		}
		return roleList;
	}

}
