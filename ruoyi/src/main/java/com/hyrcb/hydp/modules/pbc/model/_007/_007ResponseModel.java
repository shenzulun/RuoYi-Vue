/**
 * File Name: _007ResponseModel.java
 * Date: 2020-09-30 08:14:41
 */
package com.hyrcb.hydp.modules.pbc.model._007;

import java.util.List;

/**
 * Description: 获取企业的地图坐标信息
 * @author shenzulun
 * @date 2020-09-30
 * @version 1.0
 */
public class _007ResponseModel {
	
	private List<_007ResponseItem> data_yrz;
	
	private List<_007ResponseItem> data_zzdj;
	
	private List<_007ResponseItem> data_wdj;
	
	public _007ResponseModel() {}
	
	public _007ResponseModel(List<_007ResponseItem> data_yrz, List<_007ResponseItem> data_zzdj,
			List<_007ResponseItem> data_wdj) {
		super();
		this.data_yrz = data_yrz;
		this.data_zzdj = data_zzdj;
		this.data_wdj = data_wdj;
	}

	public List<_007ResponseItem> getData_yrz() {
		return data_yrz;
	}

	public void setData_yrz(List<_007ResponseItem> data_yrz) {
		this.data_yrz = data_yrz;
	}

	public List<_007ResponseItem> getData_zzdj() {
		return data_zzdj;
	}

	public void setData_zzdj(List<_007ResponseItem> data_zzdj) {
		this.data_zzdj = data_zzdj;
	}

	public List<_007ResponseItem> getData_wdj() {
		return data_wdj;
	}

	public void setData_wdj(List<_007ResponseItem> data_wdj) {
		this.data_wdj = data_wdj;
	}

}
