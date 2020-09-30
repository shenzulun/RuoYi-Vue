/**
 * File Name: _002ResponseModel.java
 * Date: 2020-09-29 15:11:45
 */
package com.hyrcb.hydp.modules.pbc.model._002;

/**
 * Description: 惠企政策条目数
 * @author shenzulun
 * @date 2020-09-29
 * @version 1.0
 */
public class _002ResponseModel {

	private String[] xAxis;
	
	private int[] data;
	
	public _002ResponseModel() {}
	
	public _002ResponseModel(String[] xAxis, int[] data) {
		super();
		this.xAxis = xAxis;
		this.data = data;
	}

	public String[] getxAxis() {
		return xAxis;
	}

	public void setxAxis(String[] xAxis) {
		this.xAxis = xAxis;
	}

	public int[] getData() {
		return data;
	}

	public void setData(int[] data) {
		this.data = data;
	}
	
}
