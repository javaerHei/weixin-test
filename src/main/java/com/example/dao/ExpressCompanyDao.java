package com.example.dao;

import java.util.List;

import com.example.entity.ExpressCompany;

/**
 * 快递物流公司dao
 * <br>创建日期：2016年8月3日
 * @author gongmingguo
 * @since 1.1
 * @version 1.1
 */
public interface ExpressCompanyDao {
	
	/**
	 * 查询快递公司列表
	 * @since 1.1
	 * @return
	 * <br><b>作者： @author gongmingguo</b>
	 * <br>创建时间：2016年8月15日 上午10:53:38
	 */
	List<ExpressCompany> findActiveExpressCompanyList();

	/**
	 * 查询快递100对应的快递公司code
	 * @since 1.1
	 * @param expressCompanyId
	 * @return
	 * <br><b>作者： @author gongmingguo</b>
	 * <br>创建时间：2016年8月15日 上午10:53:14
	 */
	String getKuaidi100Code(String expressCompanyId);

	/**
	 * 查询haoservice对应的快递公司code
	 * @since 1.1
	 * @param expressCompanyId
	 * @return
	 * <br><b>作者： @author gongmingguo</b>
	 * <br>创建时间：2016年8月15日 上午10:53:14
	 */
	String getHaoserviceKuaidiCode(String expressCompanyId);
}
