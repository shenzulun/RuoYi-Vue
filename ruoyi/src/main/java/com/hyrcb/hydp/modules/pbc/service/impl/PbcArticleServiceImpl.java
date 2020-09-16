package com.hyrcb.hydp.modules.pbc.service.impl;

import java.util.Date;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyrcb.hydp.modules.pbc.mapper.PbcArticleMapper;
import com.hyrcb.hydp.modules.pbc.domain.PbcArticle;
import com.hyrcb.hydp.modules.pbc.service.IPbcArticleService;
import com.ruoyi.common.utils.SecurityUtils;

/**
 * 文章信息Service业务层处理
 *
 * @author shenzulun
 * @date 2020-09-11
 */
@Service
public class PbcArticleServiceImpl extends ServiceImpl<PbcArticleMapper, PbcArticle> implements IPbcArticleService {

	public boolean addPbcArticle(PbcArticle pbcArticle) {
		//一些动态参数
		if(pbcArticle.getCreateUser() == null) {
			pbcArticle.setCreateUser(SecurityUtils.getUsername());
		}
		pbcArticle.setCreateTime(new Date());
		pbcArticle.setStatus("1");
		return save(pbcArticle);
	}

	public boolean updatePbcArticle(PbcArticle pbcArticle) {
		pbcArticle.setUpdateUser(SecurityUtils.getUsername());
		pbcArticle.setUpdateTime(new Date());
		return updateById(pbcArticle);
	}

}
