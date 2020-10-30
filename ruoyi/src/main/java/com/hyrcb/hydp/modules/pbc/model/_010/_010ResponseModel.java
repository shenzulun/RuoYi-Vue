/**
 * File Name: _010ResponseModel.java
 * Date: 2020-10-27 10:35:58
 */
package com.hyrcb.hydp.modules.pbc.model._010;

/**
 * Description: 三张清单
 * @author shenzulun
 * @date 2020-10-27
 * @version 1.0
 */
public class _010ResponseModel {
	
	private int id;
	
	private String bank;
	
	private String content;
	
	private String time;
	
	private String url;
	
	public _010ResponseModel() {}
	
	public _010ResponseModel(int id, String bank, String content, String time, String url) {
		super();
		this.id = id;
		this.bank = bank;
		this.content = content;
		this.time = time;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	

}
