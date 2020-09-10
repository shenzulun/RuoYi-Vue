package com.hyrcb.hydp.modules.pbc.service;

import com.hyrcb.hydp.modules.pbc.domain.PbcLoanProduct;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 贷款产品Service接口
 *
 * @author shenzulun
 * @date 2020-09-10
 */
public interface IPbcLoanProductService extends IService<PbcLoanProduct> {
	
	boolean addPbcLoanProduct(PbcLoanProduct pbcLoanProduct);
	
	boolean updatePbcLoanProduct(PbcLoanProduct pbcLoanProduct);
}
