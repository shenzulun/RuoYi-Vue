/**
 * File Name: SysRegisterController.java
 * Date: 2020-11-26 08:35:39
 */
package com.ruoyi.project.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.hyrcb.hydp.modules.pbc.domain.PbcCustinfo;
import com.hyrcb.hydp.modules.pbc.service.IPbcCustinfoService;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.redis.RedisCache;
import com.ruoyi.framework.security.RegisterBody;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.service.ISysUserService;

/**
 * Description: 用户注册
 * @author shenzulun
 * @date 2020-11-26
 * @version 1.0
 */
@RestController
public class SysRegisterController {
	
	@Autowired
    private ISysUserService userService;
    
    @Autowired
    private RedisCache redisCache;
    
    @Autowired
    private IPbcCustinfoService iPbcCustinfoService;
    
    @Log(title = "用户注册", businessType = BusinessType.INSERT)
    @PostMapping("/register")
    public AjaxResult register(@Validated @RequestBody RegisterBody registerBody) {
    	/**
    	 * 校验验证码
    	 */
    	String verifyKey = Constants.CAPTCHA_CODE_KEY + registerBody.getUuid();
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null){
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(registerBody.getUserName(), Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (registerBody.getCode() == null || !registerBody.getCode().equalsIgnoreCase(captcha)){
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(registerBody.getUserName(), Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
    	
    	SysUser sysUser = new SysUser();
    	sysUser.setUserName(registerBody.getUserName());
    	sysUser.setNickName(registerBody.getNickName());
    	sysUser.setPhonenumber(registerBody.getContactPhone());
    	sysUser.setPassword(registerBody.getPassword());
    	sysUser.setStatus("0");
    	sysUser.setDelFlag("0");
    	sysUser.setDeptId(204L);
    	Long[] roleIds = {103L};
    	sysUser.setRoleIds(roleIds);
    	
    	if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(sysUser.getUserName()))){
            return AjaxResult.error("注册用户'" + sysUser.getUserName() + "'失败，账号已存在");
        }
    	sysUser.setCreateBy("admin");
    	sysUser.setPassword(SecurityUtils.encryptPassword(sysUser.getPassword()));
    	int result = userService.insertUser(sysUser);
    	// 再插入企业信息表
    	PbcCustinfo custInfo = new PbcCustinfo();
    	custInfo.setCustName(registerBody.getNickName());
    	custInfo.setContactPerson(registerBody.getContactPerson());
    	custInfo.setContactTel(registerBody.getContactPhone());
    	custInfo.setCreateUser(registerBody.getUserName());
    	iPbcCustinfoService.addPbcCustinfo(custInfo);
        return toAjax(result);
    }
    
    /**
     * 响应返回结果
     * 
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows){
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

}
