package com.hyrcb.hydp.modules.pbc.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyrcb.hydp.modules.pbc.mapper.PbcCustinfoMapper;
import com.hyrcb.hydp.modules.pbc.domain.PbcCustinfo;
import com.hyrcb.hydp.modules.pbc.service.IPbcCustinfoService;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.system.domain.SysConfig;
import com.ruoyi.project.system.service.ISysConfigService;

/**
 * 企业信息Service业务层处理
 *
 * @author shenzulun
 * @date 2020-09-10
 */
@Service
public class PbcCustinfoServiceImpl extends ServiceImpl<PbcCustinfoMapper, PbcCustinfo> implements IPbcCustinfoService {
	
	@Autowired
    private ISysConfigService configService;

	public boolean addPbcCustinfo(PbcCustinfo pbcCustInfo) {
		//一些动态参数
		if(pbcCustInfo.getCreateUser() == null) {
			pbcCustInfo.setCreateUser(SecurityUtils.getUsername());
		}
		pbcCustInfo.setCreateTime(new Date());
		pbcCustInfo.setCustNo(getCustNo());
		pbcCustInfo.setStatus("1");
		return save(pbcCustInfo);
	}

	
	synchronized String getCustNo(){
		SysConfig sysConfig = configService.selectConfigByKey0("pbc.seq.custno");
		String custno = sysConfig.getConfigValue();
		Long custNoL = Long.valueOf(custno) + 1;
		String nextCustNo = custNoL + "";
		sysConfig.setConfigValue(nextCustNo);
		configService.updateConfig(sysConfig);
		return custno;
	}

	public boolean updateCustinfo(PbcCustinfo pbcCustInfo) {
		pbcCustInfo.setUpdateUser(SecurityUtils.getUsername());
		pbcCustInfo.setUpdateTime(new Date());
		return updateById(pbcCustInfo);
	}
}
