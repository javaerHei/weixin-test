package com.example.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 快递物流公司 <br>
 * 创建日期：2016年8月3日 <br>
 * 
 * @author gongmingguo
 * @since 1.1
 * @version 1.1
 */
public class ExpressCompany implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String companyName;// 快递公司名称
	private String companyUrl;// 快递公司url
	private String fineexCode;// 发网快递公司编码
	private String kdybCode;// 快递100快递公司编码
	private String haoserviceCode;// haoervice code
	private Date createDate;
	private Date updateDate;
	private String delFlag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the companyUrl
	 */
	public String getCompanyUrl() {
		return companyUrl;
	}

	/**
	 * @param companyUrl
	 *            the companyUrl to set
	 */
	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}

	/**
	 * @return the fineexCode
	 */
	public String getFineexCode() {
		return fineexCode;
	}

	/**
	 * @param fineexCode
	 *            the fineexCode to set
	 */
	public void setFineexCode(String fineexCode) {
		this.fineexCode = fineexCode;
	}

	/**
	 * @return the kdybCode
	 */
	public String getKdybCode() {
		return kdybCode;
	}

	/**
	 * @param kdybCode
	 *            the kdybCode to set
	 */
	public void setKdybCode(String kdybCode) {
		this.kdybCode = kdybCode;
	}

	/**
	 * @return the haoserviceCode
	 */
	public String getHaoserviceCode() {
		return haoserviceCode;
	}

	/**
	 * @param haoserviceCode
	 *            the haoserviceCode to set
	 */
	public void setHaoserviceCode(String haoserviceCode) {
		this.haoserviceCode = haoserviceCode;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the delFlag
	 */
	public String getDelFlag() {
		return delFlag;
	}

	/**
	 * @param delFlag
	 *            the delFlag to set
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

}
