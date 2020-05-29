/**
 * File Name: ColumnInfoTest.java
 * Date: 2020-05-29 09:51:47
 */
package com.hyrcb.hydp.modules.tool.dataDict.model;

import org.junit.Test;

import com.hyrcb.hydp.BaseTest;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-05-29
 * @version 1.0
 */
public class ColumnInfoTest extends BaseTest{
	
	@Test
	public void test0() {
		ColumnInfo c = new ColumnInfo();
		c.setTableName("JZFY.CUST_INFO_DETAIL");
		System.out.println(c.getSystemName());
	}

}
