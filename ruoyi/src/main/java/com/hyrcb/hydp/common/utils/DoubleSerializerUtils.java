/**
 * File Name: DoubleSerializerUtils.java
 * Date: 2020-10-23 16:52:34
 */
package com.hyrcb.hydp.common.utils;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Description: 金额格式化
 * @author shenzulun
 * @date 2020-10-23
 * @version 1.0
 */
public class DoubleSerializerUtils extends JsonSerializer<List<Double>>{

	@Override
	public void serialize(List<Double> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if(value != null && value.size() > 0) {
			List<String> list = new ArrayList<>();
			for(Double d : value) {
				if(d == null) {
					list.add("");
				}else {
					DecimalFormat df = new DecimalFormat("0.##");
					df.setRoundingMode(RoundingMode.HALF_UP);
					list.add(df.format(d.doubleValue()));
				}
			}
			gen.writeObject(list);
		}else {
			gen.writeObject(value);
		}
		
	}

}
