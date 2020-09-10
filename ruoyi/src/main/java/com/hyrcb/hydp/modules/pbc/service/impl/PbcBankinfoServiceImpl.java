package com.hyrcb.hydp.modules.pbc.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyrcb.hydp.modules.pbc.mapper.PbcBankinfoMapper;
import com.hyrcb.hydp.modules.pbc.domain.PbcBankinfo;
import com.hyrcb.hydp.modules.pbc.service.IPbcBankinfoService;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.system.domain.SysConfig;
import com.ruoyi.project.system.service.ISysConfigService;

/**
 * 银行信息Service业务层处理
 *
 * @author shenzulun
 * @date 2020-09-10
 */
@Service
public class PbcBankinfoServiceImpl extends ServiceImpl<PbcBankinfoMapper, PbcBankinfo> implements IPbcBankinfoService {

	@Autowired
    private ISysConfigService configService;
	
	public boolean addPbcBankinfo(PbcBankinfo pbcBankinfo) {
		//一些动态参数
		if(pbcBankinfo.getCreateUser() == null) {
			pbcBankinfo.setCreateUser(SecurityUtils.getUsername());
		}
		pbcBankinfo.setCreateTime(new Date());
		pbcBankinfo.setBankId(getBankId());
		pbcBankinfo.setStatus("1");
		return save(pbcBankinfo);
	}
	
	synchronized String getBankId(){
		SysConfig sysConfig = configService.selectConfigByKey0("pbc.seq.bankid");
		String bankId = sysConfig.getConfigValue();
		Long bankIdL = Long.valueOf(bankId) + 1;
		String nextbankId = bankIdL + "";
		sysConfig.setConfigValue(nextbankId);
		configService.updateConfig(sysConfig);
		return bankId;
	}

	public boolean updatePbcBankinfo(PbcBankinfo pbcBankinfo) {
		pbcBankinfo.setUpdateUser(SecurityUtils.getUsername());
		pbcBankinfo.setUpdateTime(new Date());
		return updateById(pbcBankinfo);
	}

}
