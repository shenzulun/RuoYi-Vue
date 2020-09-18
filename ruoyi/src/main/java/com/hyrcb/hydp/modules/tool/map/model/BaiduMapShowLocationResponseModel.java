/**
 * File Name: BaiduMapShowLocationResponseModel.java
 * Date: 2020-09-18 16:41:33
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
public class BaiduMapShowLocationResponseModel {
	
	private BaiduMapLocation location;
	
	private int precise;
	
	private int confidence;
	
	private int comprehension;
	
	private String level;
	
	public BaiduMapShowLocationResponseModel() {}

	public BaiduMapLocation getLocation() {
		return location;
	}

	public void setLocation(BaiduMapLocation location) {
		this.location = location;
	}

	public int getPrecise() {
		return precise;
	}

	public void setPrecise(int precise) {
		this.precise = precise;
	}

	public int getConfidence() {
		return confidence;
	}

	public void setConfidence(int confidence) {
		this.confidence = confidence;
	}

	public int getComprehension() {
		return comprehension;
	}

	public void setComprehension(int comprehension) {
		this.comprehension = comprehension;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
}
