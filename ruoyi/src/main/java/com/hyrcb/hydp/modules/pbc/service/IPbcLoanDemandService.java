package com.hyrcb.hydp.modules.pbc.service;

import com.hyrcb.hydp.modules.pbc.domain.PbcLoanDemand;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 企业融资需求Service接口
 *
 * @author shenzulun
 * @date 2020-09-10
 */
public interface IPbcLoanDemandService extends IService<PbcLoanDemand> {
	
	boolean addPbcLoanDemand(PbcLoanDemand pbcLoanDemand);
	
	boolean updatePbcLoanDemand(PbcLoanDemand pbcLoanDemand);

}
