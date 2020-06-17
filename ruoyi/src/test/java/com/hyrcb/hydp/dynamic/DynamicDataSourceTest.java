/**
 * File Name: DynamicDataSourceTest.java
 * Date: 2020-06-04 16:19:35
 */
package com.hyrcb.hydp.dynamic;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.LaunchEntry;
import com.hyrcb.hydp.dynamic.domain.User;
import com.hyrcb.hydp.dynamic.service.UserService;
import com.hyrcb.hydp.modules.crm.service.ICustinfo1Service;

/**
 * Description: 多数据源测试类
 * @author shenzulun
 * @date 2020-06-04
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LaunchEntry.class)
public class DynamicDataSourceTest {
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private ICustinfo1Service iCustinfo1Service;
	
	@Test
	public void test1() {
		List<User> userList = userService.list();
		System.out.println(userList.get(0));
	}
	
	@Test
	public void test2() {
		int count = iCustinfo1Service.count();
		System.out.println("crm_custinfo: " + count);
	}
	

}
