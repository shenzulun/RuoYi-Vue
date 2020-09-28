/**
 * File Name: ToolController.java
 * Date: 2020-05-28 09:26:05
 */
package com.hyrcb.hydp.modules.tool.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hyrcb.hydp.modules.tool.map.BaiduMapUtils;
import com.hyrcb.hydp.modules.tool.map.model.BaiduMapLocation;
import com.ruoyi.framework.web.domain.AjaxResult;

/**
 * Description: 工具路由控制类
 * @author shenzulun
 * @date 2020-05-28
 * @version 1.0
 */
@RestController
@RequestMapping("/tool" )
public class ToolController {
	
	@RequestMapping("/showLocation" )
	public AjaxResult showLocation(@RequestParam(name="address") String address) {
		BaiduMapLocation location = BaiduMapUtils.showLocationDetail(address);
		return AjaxResult.success(location);
	}

}
