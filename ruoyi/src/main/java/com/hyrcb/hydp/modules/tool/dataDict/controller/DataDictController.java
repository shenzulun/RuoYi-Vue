/**
 * File Name: DataDictController.java
 * Date: 2020-05-28 18:11:42
 */
package com.hyrcb.hydp.modules.tool.dataDict.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hyrcb.hydp.common.config.StaticCode;
import com.hyrcb.hydp.common.dto.CommonSearchDTO;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.hyrcb.hydp.modules.tool.dataDict.core.DataDictQuery;
import com.hyrcb.hydp.modules.tool.dataDict.model.ColumnInfo;

/**
 * Description: 数据字典的路由控制类
 * @author shenzulun
 * @date 2020-05-28
 * @version 1.0
 */
@RestController
@RequestMapping("/tool/dict" )
public class DataDictController extends BaseController{

	
	@RequestMapping("/list")
	public TableDataInfo list(CommonSearchDTO commonSearchDTO){
		String queryValue = commonSearchDTO.getQueryValue();  //查询条件
		logger.info("开始查询数仓数据字典,输入条件: {}", queryValue);
		List<ColumnInfo> columns = DataDictQuery.dictQuery(queryValue,StaticCode.CACHE_PREFIX_DATA_STORAGE,2);
		logger.info("条件[{}]查询完成", queryValue);
		return getDataTable(columns);
	}
}
