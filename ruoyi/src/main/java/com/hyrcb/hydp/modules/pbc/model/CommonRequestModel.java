/**
 * File Name: CommonRequestModel.java
 * Date: 2020-09-29 11:14:41
 */
package com.hyrcb.hydp.modules.pbc.model;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-09-29
 * @version 1.0
 */
public class CommonRequestModel {
	
	private String dataDt;
	
	private String custType;
	
	private String addressCode;

	public String getDataDt() {
		return dataDt;
	}

	public void setDataDt(String dataDt) {
		this.dataDt = dataDt;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getAddressCode() {
		return addressCode;
	}

	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}
	

}
