package com.hyrcb.hydp.modules.pbc.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyrcb.hydp.modules.pbc.mapper.PbcLoanDemandMapper;
import com.hyrcb.hydp.modules.pbc.domain.PbcLoanDemand;
import com.hyrcb.hydp.modules.pbc.service.IPbcLoanDemandService;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.system.domain.SysConfig;
import com.ruoyi.project.system.service.ISysConfigService;

import me.belucky.easytool.util.DateTimeUtils;
import me.belucky.easytool.util.StringUtils;

/**
 * 企业融资需求Service业务层处理
 *
 * @author shenzulun
 * @date 2020-09-10
 */
@Service
public class PbcLoanDemandServiceImpl extends ServiceImpl<PbcLoanDemandMapper, PbcLoanDemand> implements IPbcLoanDemandService {

	@Autowired
    private ISysConfigService configService;
	
	public boolean addPbcLoanDemand(PbcLoanDemand pbcLoanDemand) {
		//一些动态参数
		if(pbcLoanDemand.getCreateUser() == null) {
			pbcLoanDemand.setCreateUser(SecurityUtils.getUsername());
		}
		pbcLoanDemand.setCreateTime(new Date());
		pbcLoanDemand.setDemandNo(getDemandNo());
		pbcLoanDemand.setStatus("1");
		return save(pbcLoanDemand);
	}
	
	synchronized String getDemandNo(){
		//当日
		String today = DateTimeUtils.getToday();
		
		SysConfig sysConfig = configService.selectConfigByKey0("pbc.seq.demandNo");
		String seq = sysConfig.getConfigValue();
		int seqI = Integer.valueOf(seq);
		
		if(seqI >= 999999) {
			seqI = 1;
		}
		sysConfig.setConfigValue(seqI + "");
		configService.updateConfig(sysConfig);
		return today + (StringUtils.intToStringBySpecifiedLength(Integer.valueOf(seq), 6, true, "0"));
	}

	public boolean updatePbcLoanDemand(PbcLoanDemand pbcLoanDemand) {
		pbcLoanDemand.setUpdateUser(SecurityUtils.getUsername());
		pbcLoanDemand.setUpdateDate(new Date());
		return updateById(pbcLoanDemand);
	}

}
