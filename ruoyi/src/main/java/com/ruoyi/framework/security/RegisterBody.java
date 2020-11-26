/**
 * File Name: RegisterBody.java
 * Date: 2020-10-12 16:53:14
 */
package com.ruoyi.framework.security;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Description: 用户注册body
 * @author shenzulun
 * @date 2020-10-12
 * @version 1.0
 */
public class RegisterBody {

	/**
	 * 用户类型  默认是企业客户
	 */
	private String userType;
	/**
	 * 用户昵称  
	 */
	@NotBlank(message = "用户名称不能为空")
    @Size(min = 0, max = 128, message = "用户名称长度不能超过128个字符")
	private String nickName;
	/**
	 * 联系人
	 */
	@Size(min = 0, max = 128, message = "联系人长度不能超过128个字符")
	private String contactPerson;
	/**
	 * 联系电话
	 */
	@Size(min = 0, max = 20, message = "联系电话长度不能超过20个字符")
	private String contactPhone;
	 /**
     * 用户名
     * 对应登录的账号
     */
	@NotBlank(message = "登录账号不能为空")
    @Size(min = 0, max = 30, message = "登录账号长度不能超过15个字符")
    private String userName;

    /**
     * 用户密码
     */
	@NotBlank(message = "用户密码不能为空")
    @Size(min = 5, max = 30, message = "用户密码最少6位")
    private String password;

    /**
     * 验证码
     */
	@NotBlank(message = "验证码不能为空")
    private String code;

    /**
     * 唯一标识
     */
	@NotBlank(message = "验证码不能为空")
    private String uuid = "";

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
