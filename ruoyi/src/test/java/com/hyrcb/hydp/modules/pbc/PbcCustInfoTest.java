/**
 * File Name: PbcCustInfoTest.java
 * Date: 2020-09-10 16:55:54
 */
package com.hyrcb.hydp.modules.pbc;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.LaunchEntry;
import com.hyrcb.hydp.modules.pbc.domain.PbcCustinfo;
import com.hyrcb.hydp.modules.pbc.service.IPbcCustinfoService;

import me.belucky.easytool.util.FileTools;
import me.belucky.easytool.util.StringUtils;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-09-10
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LaunchEntry.class)
public class PbcCustInfoTest {
	
	@Autowired
    private IPbcCustinfoService iPbcCustinfoService;
	
//	@Test
	public void testSaveBatch1() {
		List<String> list = FileTools.getContentList("E:/人行三色图/数据录入/浙江省参与防控疫情企业重点名单.txt", true, "UTF-8");
		
		for(String line : list) {
			/**
			 * 浙江黄岩求新旅游用品厂|91331003148150259N|黄岩|浙江省台州市黄岩大桥路616号|防护服|郑懿|13676672772
			 */
			String[] arr = StringUtils.split(line, '|');
			PbcCustinfo cust = new PbcCustinfo();
			cust.setCustName(arr[0]);
			cust.setIdenType("204");
			cust.setIdenNo(arr[1]);
			cust.setAddress(arr[3]);
			cust.setIndustry(arr[4]);
			cust.setContactPerson(arr[5]);
			cust.setContactTel(arr[6]);
			cust.setCreateUser("admin");
			cust.setStatus("1");
			cust.setTag("浙江省参与防控疫情企业重点名单");
			iPbcCustinfoService.addPbcCustinfo(cust);
		}
	}
	
//	@Test
	public void testSaveBatch2() {
		List<String> list = FileTools.getContentList("E:/人行三色图/数据录入/农商行+民生.txt", true, "UTF-8");
		
		for(String line : list) {
			/**
			 * 台州市黄岩胜杰模配有限公司|91331003MA29WHUR1C|浙江省台州市黄岩区新前街道牟村村|黄杰|台州市黄岩区市场监督管理局|批发和零售业
			 */
			String[] arr = StringUtils.split(line, '|');
			PbcCustinfo cust = new PbcCustinfo();
			cust.setCustName(arr[0]);
			cust.setIdenType("204");
			cust.setIdenNo(arr[1]);
			cust.setAddress(arr[2]);
			cust.setIndustry(arr[5]);
			cust.setContactPerson(arr[3]);
//			cust.setContactTel(arr[6]);
			cust.setCreateUser("admin");
			cust.setStatus("1");
			cust.setTag("农商行+民生");
			iPbcCustinfoService.addPbcCustinfo(cust);
		}
	}
	
//	@Test
	public void testSaveBatch3() {
		List<String> list = FileTools.getContentList("E:/人行三色图/数据录入/到四月份有订单外贸企业名单.txt", true, "UTF-8");
		
		for(String line : list) {
			/**
			 *33239629A2|台州市黄岩通升模具有限公司|浙江省台州黄岩区北城街道
			 */
			String[] arr = StringUtils.split(line, '|');
			PbcCustinfo cust = new PbcCustinfo();
			cust.setCustName(arr[1]);
			cust.setIdenType("206");
			cust.setIdenNo(arr[0]);
			cust.setAddress(arr[2]);
//			cust.setIndustry(arr[5]);
//			cust.setContactPerson(arr[3]);
//			cust.setContactTel(arr[6]);
			cust.setCreateUser("admin");
			cust.setStatus("1");
			cust.setTag("到四月份有订单外贸企业名单");
			iPbcCustinfoService.addPbcCustinfo(cust);
		}
	}
	
	@Test
	public void testSaveBatch4() {
		List<String> list = FileTools.getContentList("E:/人行三色图/数据录入/无贷户企业清单分配.txt", true, "UTF-8");
		
		for(String line : list) {
			/**
			 * 台州市黄岩棽呗童装商行|91330502MA28C1655R|浙江省台州市黄岩区印山路888（黄岩电子商务产业园）二楼0184号工位|叶锡鹏|台州市黄岩区市场监督管理局|批发和零售业
			 */
			String[] arr = StringUtils.split(line, '|');
			PbcCustinfo cust = new PbcCustinfo();
			cust.setCustName(arr[0]);
			cust.setIdenType("204");
			cust.setIdenNo(arr[1]);
			cust.setAddress(arr[2]);
			cust.setIndustry(arr[5]);
			cust.setContactPerson(arr[3]);
//			cust.setContactTel(arr[6]);
			cust.setCreateUser("admin");
			cust.setStatus("1");
			cust.setTag("无贷户企业清单分配");
			iPbcCustinfoService.addPbcCustinfo(cust);
		}
	}
	
}
