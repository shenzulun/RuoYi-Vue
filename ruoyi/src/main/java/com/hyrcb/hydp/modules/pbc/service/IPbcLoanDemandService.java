package com.hyrcb.hydp.modules.pbc.service;

import com.hyrcb.hydp.modules.pbc.domain.PbcLoanDemand;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.toolkit.Constants;

/**
 * 企业融资需求Service接口
 *
 * @author shenzulun
 * @date 2020-09-10
 */
public interface IPbcLoanDemandService extends IService<PbcLoanDemand> {
	
	boolean addPbcLoanDemand(PbcLoanDemand pbcLoanDemand);
	
	boolean updatePbcLoanDemand(PbcLoanDemand pbcLoanDemand);
	
//	@Select("select de.*,cu.cust_name from pbc_loan_demand de left join pbc_custinfo cu on cu.cust_no=de.cust_no ${ew.customSqlSegment}")
//	List<PbcLoanDemand> list(@Param(Constants.WRAPPER) Wrapper<PbcLoanDemand> queryWrapper);

}
