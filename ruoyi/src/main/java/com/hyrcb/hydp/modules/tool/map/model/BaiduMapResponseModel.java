/**
 * File Name: BaiduMapResponseModel.java
 * Date: 2020-09-18 17:26:10
 */
package com.hyrcb.hydp.modules.tool.map.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-09-18
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class BaiduMapResponseModel<T> {
	
	/**
	 * 0-成功
	 */
	private int status;
	
	private T result;
	
	public BaiduMapResponseModel() {}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
	
}
