/**
 * File Name: _004ResponseModel.java
 * Date: 2020-09-30 08:10:10
 */
package com.hyrcb.hydp.modules.pbc.model._004;

/**
 * Description: 分区域融资对接情况
 * @author shenzulun
 * @date 2020-09-30
 * @version 1.0
 */
public class _004ResponseModel {
	
	private String[] xAxis;
	
	private int[] data_y;
	
	private int[] data_w;
	
	public _004ResponseModel() {}
	
	public _004ResponseModel(String[] xAxis, int[] data_y, int[] data_w) {
		super();
		this.xAxis = xAxis;
		this.data_y = data_y;
		this.data_w = data_w;
	}

	public String[] getxAxis() {
		return xAxis;
	}

	public void setxAxis(String[] xAxis) {
		this.xAxis = xAxis;
	}

	public int[] getData_y() {
		return data_y;
	}

	public void setData_y(int[] data_y) {
		this.data_y = data_y;
	}

	public int[] getData_w() {
		return data_w;
	}

	public void setData_w(int[] data_w) {
		this.data_w = data_w;
	}
	
}
