package com.example.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.example.common.Constants;
import com.example.dao.ExpressCompanyDao;
import com.example.entity.ExpressCompany;

/**
 * 快递物流公司dao实现 <br>
 * 创建日期：2016年8月3日 <br>
 * 
 * @author gongmingguo
 * @since 1.1
 * @version 1.1
 */
@Repository
public class ExpressCompanyDaoImpl extends BaseDaoImpl implements ExpressCompanyDao {

	@Override
	public List<ExpressCompany> findActiveExpressCompanyList() {
		return jdbcTemplate.query("select id,company_name from t_express_company where del_flag = ?",
				new Object[] { Constants.DEL_FLAG_NORMAL }, BeanPropertyRowMapper.newInstance(ExpressCompany.class));
	}

	@Override
	public String getKuaidi100Code(String expressCompanyId) {
		return jdbcTemplate.query("select kdyb_code from t_express_company where id=?",
				new Object[] { expressCompanyId }, new ResultSetExtractor<String>() {

					@Override
					public String extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getString("kdyb_code");
						}
						return null;
					}
				});
	}

	@Override
	public String getHaoserviceKuaidiCode(String expressCompanyId) {
		return jdbcTemplate.query("select haoservice_code from t_express_company where id=?",
				new Object[] { expressCompanyId }, new ResultSetExtractor<String>() {

					@Override
					public String extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getString("haoservice_code");
						}
						return null;
					}
				});
	}

}
