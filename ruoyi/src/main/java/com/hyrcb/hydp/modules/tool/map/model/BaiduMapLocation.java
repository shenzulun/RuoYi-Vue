/**
 * File Name: BaiduMapLocation.java
 * Date: 2020-09-18 16:43:33
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
public class BaiduMapLocation {
	
	private float lat;
	
	private float lng;
	
	public BaiduMapLocation() {}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLng() {
		return lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}

}
