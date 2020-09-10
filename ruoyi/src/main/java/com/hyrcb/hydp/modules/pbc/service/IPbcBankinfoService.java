package com.hyrcb.hydp.modules.pbc.service;

import com.hyrcb.hydp.modules.pbc.domain.PbcBankinfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 银行信息Service接口
 *
 * @author shenzulun
 * @date 2020-09-10
 */
public interface IPbcBankinfoService extends IService<PbcBankinfo> {
	
	boolean addPbcBankinfo(PbcBankinfo pbcBankinfo);
	
	boolean updatePbcBankinfo(PbcBankinfo pbcBankinfo);
	
}
