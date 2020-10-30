/**
 * File Name: _004ResponseModel.java
 * Date: 2020-09-30 08:10:10
 */
package com.hyrcb.hydp.modules.pbc.model._004;

import java.util.List;

/**
 * Description: 分区域融资对接情况
 * @author shenzulun
 * @date 2020-09-30
 * @version 1.0
 */
public class _004ResponseModel {
	
	private List<String> xAxis;
	
	private List<Integer> data_y;
	
	private List<Integer> data_w;
	
	private List<Integer> data_zzdj;
	
	public _004ResponseModel() {}
	
	public _004ResponseModel(List<String> xAxis, List<Integer> data_y, List<Integer> data_w) {
		super();
		this.xAxis = xAxis;
		this.data_y = data_y;
		this.data_w = data_w;
	}
	
	public _004ResponseModel(List<String> xAxis, List<Integer> data_y, List<Integer> data_w, List<Integer> data_zzdj) {
		super();
		this.xAxis = xAxis;
		this.data_y = data_y;
		this.data_w = data_w;
		this.data_zzdj = data_zzdj;
	}

	public List<String> getxAxis() {
		return xAxis;
	}

	public void setxAxis(List<String> xAxis) {
		this.xAxis = xAxis;
	}

	public List<Integer> getData_y() {
		return data_y;
	}

	public void setData_y(List<Integer> data_y) {
		this.data_y = data_y;
	}

	public List<Integer> getData_w() {
		return data_w;
	}

	public void setData_w(List<Integer> data_w) {
		this.data_w = data_w;
	}

	public List<Integer> getData_zzdj() {
		return data_zzdj;
	}

	public void setData_zzdj(List<Integer> data_zzdj) {
		this.data_zzdj = data_zzdj;
	}
	
}
