package com.example.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.example.bean.User;
import com.example.dao.UserDao;
import com.example.dto.UserDto;

@Repository
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	@Override
	public UserDto findByName(String name) {
		return jdbcTemplate.query("select * from t_user where username = ?", new Object[] { name },
				new ResultSetExtractor<UserDto>() {

					@Override
					public UserDto extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							Long userId = rs.getLong("id");
							String username = rs.getString("username");
							String password = rs.getString("password");
							UserDto user = new UserDto();
							user.setId(userId);
							user.setUsername(username);
							user.setPassword(password);
							return user;
						}
						return null;
					}
				});

	}

	@Override
	public User getList() {
		return null;
	}

}
