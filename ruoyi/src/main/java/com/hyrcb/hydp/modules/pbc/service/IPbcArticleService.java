package com.hyrcb.hydp.modules.pbc.service;

import com.hyrcb.hydp.modules.pbc.domain.PbcArticle;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 文章信息Service接口
 *
 * @author shenzulun
 * @date 2020-09-11
 */
public interface IPbcArticleService extends IService<PbcArticle> {
	
	boolean addPbcArticle(PbcArticle pbcArticle);
	
	boolean updatePbcArticle(PbcArticle pbcArticle);
}
