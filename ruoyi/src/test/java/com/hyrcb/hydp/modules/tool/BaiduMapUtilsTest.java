/**
 * File Name: BaiduMapUtilsTest.java
 * Date: 2020-09-18 15:55:22
 */
package com.hyrcb.hydp.modules.tool;

import org.junit.Test;
import com.hyrcb.hydp.common.utils.JsonUtils;
import com.hyrcb.hydp.modules.tool.map.BaiduMapUtils;
import com.hyrcb.hydp.modules.tool.map.model.BaiduMapLocation;
import com.hyrcb.hydp.modules.tool.map.model.BaiduMapResponseModel;
import com.hyrcb.hydp.modules.tool.map.model.BaiduMapShowLocationResponseModel;
import me.belucky.easytool.util.CommonUtils;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-09-18
 * @version 1.0
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = LaunchEntry.class)
public class BaiduMapUtilsTest {
	
	@Test
	public void test1() {
		String location = BaiduMapUtils.showLocation("台州市黄岩区洞天路88号");
		System.out.println(location);
	}
	
//	@Test
	public void test2() {
		String str = "showLocation&&showLocation({\"status\":0,\"result\":{\"location\":{\"lng\":121.26735117560445,\"lat\":28.641923078787408},\"precise\":1,\"confidence\":80,\"comprehension\":100,\"level\":\"门址\"}})";
		String str1 = CommonUtils.regexMatch(str, "showLocation\\((.+?)\\)");
		System.out.println(str1);
		
		BaiduMapResponseModel<BaiduMapShowLocationResponseModel> resp = JsonUtils.toObject(str1, BaiduMapResponseModel.class, BaiduMapShowLocationResponseModel.class);
		BaiduMapLocation location = resp.getResult().getLocation();
		System.out.println(location.getLat() + "," + location.getLng());
		
	}

}
