/**
 * File Name: _005ResponseModel.java
 * Date: 2020-09-30 08:11:54
 */
package com.hyrcb.hydp.modules.pbc.model._005;

import java.util.List;

/**
 * Description: 企业融资覆盖情况
 * @author shenzulun
 * @date 2020-09-30
 * @version 1.0
 */
public class _005ResponseModel {
	
	private List<String> xAxis;
	
	private List<Double> data_fg;
	
	private List<Double> data_cj;
	
	public _005ResponseModel() {}
	
	public _005ResponseModel(List<String> xAxis, List<Double> data_fg, List<Double> data_cj) {
		super();
		this.xAxis = xAxis;
		this.data_fg = data_fg;
		this.data_cj = data_cj;
	}

	public List<String> getxAxis() {
		return xAxis;
	}

	public void setxAxis(List<String> xAxis) {
		this.xAxis = xAxis;
	}

	public List<Double> getData_fg() {
		return data_fg;
	}

	public void setData_fg(List<Double> data_fg) {
		this.data_fg = data_fg;
	}

	public List<Double> getData_cj() {
		return data_cj;
	}

	public void setData_cj(List<Double> data_cj) {
		this.data_cj = data_cj;
	}
	
}
