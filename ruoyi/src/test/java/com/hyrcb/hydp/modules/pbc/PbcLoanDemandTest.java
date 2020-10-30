/**
 * File Name: PbcLoanDemandTest.java
 * Date: 2020-10-14 09:01:25
 */
package com.hyrcb.hydp.modules.pbc;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.LaunchEntry;
import com.hyrcb.hydp.modules.pbc.domain.PbcLoanDemand;
import com.hyrcb.hydp.modules.pbc.service.IPbcLoanDemandService;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-10-14
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LaunchEntry.class)
public class PbcLoanDemandTest {

	@Autowired
    private IPbcLoanDemandService iPbcLoanDemandService;
	
	@Test
	public void testRandom() {
		// 1. 查询出所有的企业信息
		List<Record> custList = Db.find("select cust_no from pbc_custinfo");
		
		// 2. 查询出所有的银行信息
		List<Record> bankList = Db.find("select bank_id from pbc_bankinfo");
		
		int generateSize = 500;
		String[] bzfs = {"1","2","3","4"};   //担保方式
		
		String[] jjzt = {"1","2","3","1","1"};  //融资解决状态
		
		LocalDateTime now = LocalDateTime.now();
		
		for(int i=0;i<generateSize;i++) {
			PbcLoanDemand pbcLoanDemand = new PbcLoanDemand();
			//客户号
			String custNo = random(custList, "cust_no");
			pbcLoanDemand.setCustNo(custNo);
			//银行编号
			String bankNo = random(bankList, "bank_id");
			pbcLoanDemand.setSolveBankNo(bankNo);
			//融资类型
			pbcLoanDemand.setLoanType("1");
			//融资金额
			int rzje = new Random().nextInt(500) * 10000;
			pbcLoanDemand.setLoanAmount(rzje + "");
			//担保方式
			pbcLoanDemand.setLoanType(bzfs[new Random().nextInt(bzfs.length)]);
			//融资解决状态
			pbcLoanDemand.setSolveStatus(jjzt[new Random().nextInt(jjzt.length)]);
			
			pbcLoanDemand.setCreateUser("admin");
			int x = i % 10;
			LocalDateTime newDate = now.plus(0 - x, ChronoUnit.MONTHS);
			pbcLoanDemand.setCreateTime(Date.from(newDate.atZone(ZoneId.systemDefault()).toInstant()));
			iPbcLoanDemandService.addPbcLoanDemand(pbcLoanDemand);
			System.out.println(custNo);
		}
		
	}
	
	public String random(List<Record> list, String objectName) {
		int size = list.size();
		int r = new Random().nextInt(size);
		return list.get(r).getStr(objectName);
	}
}
