package com.hyrcb.hydp.modules.pbc.service;

import com.hyrcb.hydp.modules.pbc.domain.PbcCustinfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 企业信息Service接口
 *
 * @author shenzulun
 * @date 2020-09-10
 */
public interface IPbcCustinfoService extends IService<PbcCustinfo> {
	
	/**
	 * 新增客户信息
	 * @param pbcCustInfo
	 * @return
	 */
	boolean addPbcCustinfo(PbcCustinfo pbcCustInfo);
	
	/**
	 * 更新客户信息
	 * @param pbcCustInfo
	 * @return
	 */
	boolean updateCustinfo(PbcCustinfo pbcCustInfo);
	
}
