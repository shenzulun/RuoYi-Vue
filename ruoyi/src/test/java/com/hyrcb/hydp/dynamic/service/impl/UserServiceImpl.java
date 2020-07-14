/**
 * File Name: UserServiceImpl.java
 * Date: 2020-06-04 16:44:19
 */
package com.hyrcb.hydp.dynamic.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyrcb.hydp.dynamic.domain.User;
import com.hyrcb.hydp.dynamic.mapper.UserMapper;
import com.hyrcb.hydp.dynamic.service.UserService;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-06-04
 * @version 1.0
 */
@Service
@DS("slave1")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

}
