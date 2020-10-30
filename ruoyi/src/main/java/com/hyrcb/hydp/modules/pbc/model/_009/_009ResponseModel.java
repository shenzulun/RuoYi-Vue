/**
 * File Name: _009ResponseModel.java
 * Date: 2020-10-27 10:34:15
 */
package com.hyrcb.hydp.modules.pbc.model._009;

/**
 * Description: 银行特色企业贷款产品
 * @author shenzulun
 * @date 2020-10-27
 * @version 1.0
 */
public class _009ResponseModel {
	
	private int id;
	
	private String bank;
	
	private String name;
	
	private String remark;
	
	private String url;
	
	public _009ResponseModel() {}
	
	public _009ResponseModel(int id, String bank, String name, String remark, String url) {
		super();
		this.id = id;
		this.bank = bank;
		this.name = name;
		this.remark = remark;
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
