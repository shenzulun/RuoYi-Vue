/**
 * File Name: _003ResponseModel.java
 * Date: 2020-09-29 15:56:58
 */
package com.hyrcb.hydp.modules.pbc.model._003;

/**
 * Description: 企业融资情况
 * @author shenzulun
 * @date 2020-09-29
 * @version 1.0
 */
public class _003ResponseModel {
	
	/**
	 * 正在对接
	 */
	private int zzdj;
	/**
	 * 已融资
	 */
	private int yrz;
	/**
	 * 未对接
	 */
	private int wdj;
	
	public _003ResponseModel() {}
	
	public _003ResponseModel(int zzdj, int yrz, int wdj) {
		super();
		this.zzdj = zzdj;
		this.yrz = yrz;
		this.wdj = wdj;
	}

	public int getZzdj() {
		return zzdj;
	}

	public void setZzdj(int zzdj) {
		this.zzdj = zzdj;
	}

	public int getYrz() {
		return yrz;
	}

	public void setYrz(int yrz) {
		this.yrz = yrz;
	}

	public int getWdj() {
		return wdj;
	}

	public void setWdj(int wdj) {
		this.wdj = wdj;
	}

}
