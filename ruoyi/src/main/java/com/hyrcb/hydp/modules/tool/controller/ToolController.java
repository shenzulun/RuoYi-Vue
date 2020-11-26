/**
 * File Name: ToolController.java
 * Date: 2020-05-28 09:26:05
 */
package com.hyrcb.hydp.modules.tool.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hyrcb.hydp.modules.tool.map.BaiduMapUtils;
import com.hyrcb.hydp.modules.tool.map.model.BaiduMapLocation;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;

import me.belucky.easytool.task.TaskInitCenter;

/**
 * Description: 工具路由控制类
 * @author shenzulun
 * @date 2020-05-28
 * @version 1.0
 */
@RestController
@RequestMapping("/tool" )
public class ToolController extends BaseController{
	
	@RequestMapping("/showLocation" )
	public AjaxResult showLocation(@RequestParam(name="address") String address) {
		BaiduMapLocation location = BaiduMapUtils.showLocationDetail(address);
		return AjaxResult.success(location);
	}
	
	@RequestMapping("/addressCodeDetail" )
	public AjaxResult addressCodeDetail(@RequestParam(name="code") String code) {
		List<Record> list = Db.find("select * from sys_dict_tree where type='ADDRESS_CODE' and code = ?", code);
		if(list == null || list.size() == 0) {
			return AjaxResult.error("地址代码不存在");
		}else {
			return AjaxResult.success(list.get(0).getColumns());
		}
	}
	
	@RequestMapping("/refreshCache" )
	public AjaxResult refreshCache() {
		try {
			TaskInitCenter.runTask("SystemLoadCacheTask");
			logger.info("缓存刷新成功");
		} catch (Exception e) {
			logger.error("缓存刷新失败...", e);
		}
		return AjaxResult.success();
	}

}
