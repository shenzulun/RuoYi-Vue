package com.hyrcb.hydp.modules.pbc.service.impl;

import java.util.Date;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyrcb.hydp.modules.pbc.mapper.PbcActicleMapper;
import com.hyrcb.hydp.modules.pbc.domain.PbcActicle;
import com.hyrcb.hydp.modules.pbc.service.IPbcActicleService;
import com.ruoyi.common.utils.SecurityUtils;

/**
 * 文章信息Service业务层处理
 *
 * @author shenzulun
 * @date 2020-09-10
 */
@Service
public class PbcActicleServiceImpl extends ServiceImpl<PbcActicleMapper, PbcActicle> implements IPbcActicleService {
	
	public boolean addPbcActicle(PbcActicle pbcActicle) {
		//一些动态参数
		if(pbcActicle.getCreateUser() == null) {
			pbcActicle.setCreateUser(SecurityUtils.getUsername());
		}
		pbcActicle.setCreateTime(new Date());
		pbcActicle.setStatus("1");
		return save(pbcActicle);
	}

	public boolean updatePbcActicle(PbcActicle pbcActicle) {
		pbcActicle.setUpdateUser(SecurityUtils.getUsername());
		pbcActicle.setUpdateTime(new Date());
		return updateById(pbcActicle);
	}

}
