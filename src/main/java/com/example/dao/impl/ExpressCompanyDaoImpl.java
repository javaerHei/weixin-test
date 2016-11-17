package com.example.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

	@Override
	public long addExpressCompany(String companyName, String companyUrl, String fineexCode, String kdybCode,
			String haoserviceCode) {
		String sql = "INSERT INTO `t_express_company`(`company_name`, `company_url`, `fineex_code`, `kdyb_code`,"
				+ " `haoservice_code`, `created_date`, `updated_date`, `del_flag`) VALUES (?,?,?,?,?,NOW(),NOW(),?)";
		return super.insert(sql, new Object[] { companyName, companyUrl, fineexCode, kdybCode, haoserviceCode,
				Constants.DEL_FLAG_NORMAL }, "id");
	}

	@Override
	public void batchAdd(List<ExpressCompany> expressCompanyList) {
		List<Object[]> paramList = new ArrayList<>();
		for (ExpressCompany expressCompany : expressCompanyList) {
			Object[] paramArray = new Object[] { expressCompany.getCompanyName(), expressCompany.getCompanyUrl(),
					expressCompany.getFineexCode(), expressCompany.getKdybCode(), expressCompany.getHaoserviceCode(),
					Constants.DEL_FLAG_NORMAL };
			paramList.add(paramArray);
		}

		jdbcTemplate.batchUpdate(
				"INSERT INTO `t_express_company`(`company_name`, `company_url`, `fineex_code`, `kdyb_code`,"
						+ " `haoservice_code`, `created_date`, `updated_date`, `del_flag`) VALUES (?,?,?,?,?,NOW(),NOW(),?)",
				paramList);
	}

}
