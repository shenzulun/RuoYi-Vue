/**
 * File Name: _006ResponseModel.java
 * Date: 2020-09-30 08:13:34
 */
package com.hyrcb.hydp.modules.pbc.model._006;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hyrcb.hydp.common.utils.DoubleSerializerUtils;

/**
 * Description: 分机构融资对接情况
 * @author shenzulun
 * @date 2020-09-30
 * @version 1.0
 */
public class _006ResponseModel {
	
	private List<String> xAxis = new ArrayList<>();
	
	@JsonSerialize(using = DoubleSerializerUtils.class)
	private List<Double> data = new ArrayList<>();
	
	private List<Integer> data_bs = new ArrayList<>();
	
	public _006ResponseModel() {}
	
	public _006ResponseModel(List<String> xAxis, List<Double> data, List<Integer> data_bs) {
		super();
		this.xAxis = xAxis;
		this.data = data;
		this.data_bs = data_bs;
	}

	public List<String> getxAxis() {
		return xAxis;
	}

	public void setxAxis(List<String> xAxis) {
		this.xAxis = xAxis;
	}

	public List<Double> getData() {
		return data;
	}

	public void setData(List<Double> data) {
		this.data = data;
	}

	public List<Integer> getData_bs() {
		return data_bs;
	}

	public void setData_bs(List<Integer> data_bs) {
		this.data_bs = data_bs;
	}
	
}
