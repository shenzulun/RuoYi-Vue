/**
 * File Name: DataAuth.java
 * Date: 2020-09-16 15:27:07
 */
package com.hyrcb.hydp.common.core.dataAuth;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-09-16
 * @version 1.0
 */
public class DataAuth {
	
	private Long roleId;
	
	private String mapperName;
	
	private String dataScope;
	
	private String deptAlias;
	
	public DataAuth() {}
	
	public DataAuth(Long roleId, String mapperName, String dataScope, String deptAlias) {
		super();
		this.roleId = roleId;
		this.mapperName = mapperName;
		this.dataScope = dataScope;
		this.deptAlias = deptAlias;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getMapperName() {
		return mapperName;
	}

	public void setMapperName(String mapperName) {
		this.mapperName = mapperName;
	}

	public String getDataScope() {
		return dataScope;
	}

	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}

	public String getDeptAlias() {
		return deptAlias;
	}

	public void setDeptAlias(String deptAlias) {
		this.deptAlias = deptAlias;
	}
	
	
}
