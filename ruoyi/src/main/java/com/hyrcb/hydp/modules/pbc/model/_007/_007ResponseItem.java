/**
 * File Name: _007ResponseItem.java
 * Date: 2020-09-30 08:15:35
 */
package com.hyrcb.hydp.modules.pbc.model._007;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-09-30
 * @version 1.0
 */
public class _007ResponseItem {
	
	private String name;
	
	private double[] value;
	
	public _007ResponseItem() {}
	
	public _007ResponseItem(String name, double[] value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double[] getValue() {
		return value;
	}

	public void setValue(double[] value) {
		this.value = value;
	}
	
}
