/**
 * File Name: _008ResponseModel.java
 * Date: 2020-10-27 10:12:42
 */
package com.hyrcb.hydp.modules.pbc.model._008;

/**
 * Description: 惠企政策列表查询
 * @author shenzulun
 * @date 2020-10-27
 * @version 1.0
 */
public class _008ResponseModel {
	
	private int id;
	
	private String dept;
	
	private String time;
	
	private String name;
	
	private String url;
	
	public _008ResponseModel() {}

	public _008ResponseModel(int id, String dept, String time, String name) {
		super();
		this.id = id;
		this.dept = dept;
		this.time = time;
		this.name = name;
	}
	
	public _008ResponseModel(int id, String dept, String time, String name, String url) {
		super();
		this.id = id;
		this.dept = dept;
		this.time = time;
		this.name = name;
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	

}
