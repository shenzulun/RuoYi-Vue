package com.hyrcb.hydp.modules.pbc.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyrcb.hydp.modules.pbc.mapper.PbcLoanProductMapper;
import com.hyrcb.hydp.modules.pbc.domain.PbcLoanProduct;
import com.hyrcb.hydp.modules.pbc.service.IPbcLoanProductService;
import com.ruoyi.common.utils.SecurityUtils;

/**
 * 贷款产品Service业务层处理
 *
 * @author shenzulun
 * @date 2020-09-10
 */
@Service
public class PbcLoanProductServiceImpl extends ServiceImpl<PbcLoanProductMapper, PbcLoanProduct> implements IPbcLoanProductService {

	public boolean addPbcLoanProduct(PbcLoanProduct pbcLoanProduct) {
		//一些动态参数
		if(pbcLoanProduct.getCreateUser() == null) {
			pbcLoanProduct.setCreateUser(SecurityUtils.getUsername());
		}
		pbcLoanProduct.setCreateTime(new Date());
		pbcLoanProduct.setStatus("1");
		return save(pbcLoanProduct);
	}

	public boolean updatePbcLoanProduct(PbcLoanProduct pbcLoanProduct) {
		pbcLoanProduct.setUpdateUser(SecurityUtils.getUsername());
		pbcLoanProduct.setUpdateTime(new Date());
		return updateById(pbcLoanProduct);
	}

}
