/**
 * File Name: BeanFactoryTest.java
 * Date: 2020-05-28 17:21:55
 */
package com.hyrcb.hydp.common.core;

import org.junit.Test;

import com.hyrcb.hydp.BaseTest;
import com.hyrcb.hydp.modules.tool.dataDict.config.DataDictConfig;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-05-28
 * @version 1.0
 */
public class BeanFactoryTest extends BaseTest{

	@Test
	public void testLoadBean() {
		DataDictConfig dictConfig = BeanFactory.getBean(DataDictConfig.class);
		System.out.println(dictConfig);
	}
}
