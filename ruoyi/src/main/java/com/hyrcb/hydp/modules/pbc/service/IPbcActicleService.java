package com.hyrcb.hydp.modules.pbc.service;

import com.hyrcb.hydp.modules.pbc.domain.PbcActicle;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 文章信息Service接口
 *
 * @author shenzulun
 * @date 2020-09-10
 */
public interface IPbcActicleService extends IService<PbcActicle> {
	
	boolean addPbcActicle(PbcActicle pbcActicle);
	
	boolean updatePbcActicle(PbcActicle pbcActicle);
}
