/**
 * File Name: _005ResponseModel.java
 * Date: 2020-09-30 08:11:54
 */
package com.hyrcb.hydp.modules.pbc.model._005;

/**
 * Description: 企业融资覆盖情况
 * @author shenzulun
 * @date 2020-09-30
 * @version 1.0
 */
public class _005ResponseModel {
	
	private String[] xAxis;
	
	private double[] data_fg;
	
	private double[] data_cj;
	
	public _005ResponseModel() {}
	
	public _005ResponseModel(String[] xAxis, double[] data_fg, double[] data_cj) {
		super();
		this.xAxis = xAxis;
		this.data_fg = data_fg;
		this.data_cj = data_cj;
	}

	public String[] getxAxis() {
		return xAxis;
	}

	public void setxAxis(String[] xAxis) {
		this.xAxis = xAxis;
	}

	public double[] getData_fg() {
		return data_fg;
	}

	public void setData_fg(double[] data_fg) {
		this.data_fg = data_fg;
	}

	public double[] getData_cj() {
		return data_cj;
	}

	public void setData_cj(double[] data_cj) {
		this.data_cj = data_cj;
	}
	
	
	
	
}
