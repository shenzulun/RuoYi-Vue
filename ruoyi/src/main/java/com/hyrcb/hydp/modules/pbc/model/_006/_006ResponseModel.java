/**
 * File Name: _006ResponseModel.java
 * Date: 2020-09-30 08:13:34
 */
package com.hyrcb.hydp.modules.pbc.model._006;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 分机构融资对接情况
 * @author shenzulun
 * @date 2020-09-30
 * @version 1.0
 */
public class _006ResponseModel {
	
	private List<String> xAxis = new ArrayList<>();
	
	private List<Integer> data = new ArrayList<>();
	
	public _006ResponseModel() {}
	
	public _006ResponseModel(List<String> xAxis, List<Integer> data) {
		super();
		this.xAxis = xAxis;
		this.data = data;
	}

	public List<String> getxAxis() {
		return xAxis;
	}

	public void setxAxis(List<String> xAxis) {
		this.xAxis = xAxis;
	}

	public List<Integer> getData() {
		return data;
	}

	public void setData(List<Integer> data) {
		this.data = data;
	}
	
}
