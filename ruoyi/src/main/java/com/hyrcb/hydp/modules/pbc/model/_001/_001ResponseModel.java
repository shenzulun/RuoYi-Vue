/**
 * File Name: _001ResponseModel.java
 * Date: 2020-09-29 11:12:51
 */
package com.hyrcb.hydp.modules.pbc.model._001;

/**
 * Description: 各类清单企业数量
 * @author shenzulun
 * @date 2020-09-29
 * @version 1.0
 */
public class _001ResponseModel {
	
	private String[] xAxis;
	
	private int[] data_y;
	
	private int[] data_w;
	
	public _001ResponseModel() {}
	
	public _001ResponseModel(String[] xAxis, int[] data_y, int[] data_w) {
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
