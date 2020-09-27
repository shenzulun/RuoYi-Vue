/**
 * File Name: BaiduMapUtils.java
 * Date: 2020-09-18 15:48:53
 */
package com.hyrcb.hydp.modules.tool.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hyrcb.hydp.common.utils.JsonUtils;
import com.hyrcb.hydp.modules.tool.map.model.BaiduMapLocation;
import com.hyrcb.hydp.modules.tool.map.model.BaiduMapResponseModel;
import com.hyrcb.hydp.modules.tool.map.model.BaiduMapShowLocationResponseModel;
import com.ruoyi.common.utils.http.HttpUtils;

import me.belucky.easytool.util.CommonUtils;

/**
 * Description: 百度地图工具类
 * @author shenzulun
 * @date 2020-09-18
 * @version 1.0
 */
public class BaiduMapUtils {
    private static final Logger log = LoggerFactory.getLogger(BaiduMapUtils.class);

	/**
	 * 地址转坐标
	 * @param adress
	 * @return
	 */
	public static String showLocation(String address) {
		String locationStr = null;
		String url = "http://api.map.baidu.com/geocoding/v3";
		String param = new StringBuilder("address=").append(address)
													.append("&output=json&ak=G3XHEHNfOZpmjuL0Ij5K0PlP3R1lqc7X&callback=showLocation")
													.toString();
		
		String jsonResult = HttpUtils.sendGet(url, param);
		log.info(jsonResult);
		String str1 = jsonResult;
		if(!jsonResult.startsWith("{")) {
			str1 = CommonUtils.regexMatch(jsonResult, "showLocation\\((.+?)\\)");
		}
		BaiduMapResponseModel<BaiduMapShowLocationResponseModel> resp = JsonUtils.toObject(str1, BaiduMapResponseModel.class, BaiduMapShowLocationResponseModel.class);
		
		if(resp != null && resp.getStatus() == 0) {
			BaiduMapLocation location = resp.getResult().getLocation();
			locationStr = location.getLat() + "," + location.getLng();
		}else {
			//log.error(resp.get);
		}
		return locationStr;
	}

}
