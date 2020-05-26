/**
 * File Name: CommonTest.java
 * Date: 2020-05-19 16:35:35
 */
package com.hyrcb.hydp;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.LaunchEntry;
import com.google.common.collect.Lists;
import com.hyrcb.hydp.modules.crm.domain.Custinfo1;
import com.hyrcb.hydp.modules.crm.service.ICustinfo1Service;

import me.belucky.easytool.random.IdCard;
import me.belucky.easytool.random.IdCardRandom;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-05-19
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LaunchEntry.class)
//@Transactional
public class CommonTest {
	
	private String[] arr1 = {"西城街道","东城街道","南城街道","北城街道"};
	private String[] arr2 = {"AAA路","BBB路","CCC路","DDD路"};
	
	@Autowired
    private ICustinfo1Service iCustinfo1Service;
	
	@Test
	public void testSelect() {
		List<Custinfo1> result = iCustinfo1Service.selectCustInfoList();
		System.out.println(result.size());
	}
	
	
//	@Test
	public void testSaveBatch() {
		List<Custinfo1> list = Lists.newArrayList();
		int total = 1000000;
		for(int i=0;i<total;i++) {
			Custinfo1 c = new Custinfo1();
			int seq = total + i;
			c.setCustNo("130" + seq);
			IdCard idcard = IdCardRandom.randOneUnFair();
			c.setCustName(idcard.getName());
			c.setCertType("101");
			c.setCertNo(idcard.getCertno());
			c.setCreateTime(new Date());
			c.setCreateBy("admin");
			
			int rand = new Random().nextInt(10000000);
			int tel = 800000000 + rand;
			c.setTelNo("18" + tel);
			
			int rand1 = new Random().nextInt(100);
			c.setAddress("浙江省台州市黄岩区" + arr1[rand % 4] + arr2[rand1 % 4] + rand1 + "号");
			list.add(c);
		}
		System.out.println("数据初始化成功...");
		iCustinfo1Service.saveBatch(list, 500);
		System.out.println("数据持久化成功...");
		
	}
	

}
