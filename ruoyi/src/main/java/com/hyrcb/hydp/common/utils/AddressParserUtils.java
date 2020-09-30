/**
 * File Name: AddressParserUtils.java
 * Date: 2020-09-30 15:47:50
 */
package com.hyrcb.hydp.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import me.belucky.easytool.util.FileTools;
import me.belucky.easytool.util.StringUtils;

/**
 * Description: 地址解析工具类
 * @author shenzulun
 * @date 2020-09-30
 * @version 1.0
 */
public class AddressParserUtils {

	public static void init() {
		String path = "F:/database/txt/address/";
		List<String> list = FileTools.getContentList(path + "data.json", true, "UTF-8");
		
		String type = "ADDRESS_CODE";
		String lev1 = "";
		String lev2 = "";
		String lev3 = "";
		String lev4 = "";
		int lastLev = 0;
		String[] lev1Arr = {"北京市","上海市","重庆市","天津市","香港特别行政区","澳门特别行政区"};
		
		List<Record> result = new ArrayList<>();
		for(String line : list) {
			if(StringUtils.isBlank(line)) {
				continue;
			}
			String[] arr = line.replaceAll("\"","").split(":");
			if(arr.length != 2) {
				continue;
			}
			int lev = 0;
			Record r = new Record();
			String code = arr[0];
			String name = arr[1].replaceAll(",", "");
			if(StringUtils.isExsit(name, lev1Arr) || name.endsWith("自治区") || name.endsWith("省")) {
				lev = 1;
				lastLev = 1;
				lev1 = code;
				lev2 = "";
				lev3 = "";
				lev4 = "";
			}else if("市辖区".equals(name) || code.endsWith("00")){
				lev = 2;
				lev2 = code;
				lev3 = "";
				lev4 = "";
			}else {
				lev = 3;
				lev3 = code;
				lev4 = "";
			}
			r.set("type", type);
			r.set("lev1", lev1);
			r.set("lev2", lev2);
			r.set("lev3", lev3);
			r.set("lev4", lev4);
			r.set("lev", lev);
			r.set("code", code);
			r.set("name", name);
			result.add(r);
		}
		Db.batchSave("sys_dict_tree", result, 500);
	}
	
	public static void initJD() {
		String path = "F:/database/txt/address/";
		List<Record> result = new ArrayList<>();
		
		List<Record> list = Db.find("select * from sys_dict_tree where lev = 3");
		for(Record record : list) {
			String code = record.getStr("code");
			String fileName = path + "town/" + code + ".json";
			File file = new File(fileName);
			if(file.exists()) {
				String json = FileTools.getContent(fileName, true, "UTF-8");
				json = json.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\"", "");
				String[] arr = json.split(",");
				for(String s : arr) {
					String[] arr1 = s.split(":");
					Record r = new Record();
					r.set("type", "ADDRESS_CODE");
					r.set("lev1", record.getStr("lev1"));
					r.set("lev2", record.getStr("lev2"));
					r.set("lev3", record.getStr("lev3"));
					r.set("lev4", arr1[0]);
					r.set("lev", 4);
					r.set("code", arr1[0]);
					r.set("name", arr1[1]);
					result.add(r);
				}
			}
		}
		Db.batchSave("sys_dict_tree", result, 500);
	}
	
	public static void main(String[] args) {
		String json = "{\"110105001\":\"建外街道\",\"110105002\":\"朝外街道\",\"110105003\":\"呼家楼街道\",\"110105004\":\"三里屯街道\",\"110105005\":\"左家庄街道\",\"110105006\":\"香河园街道\",\"110105007\":\"和平街街道\",\"110105008\":\"安贞街道\",\"110105009\":\"亚运村街道\",\"110105010\":\"小关街道\",\"110105011\":\"酒仙桥街道\",\"110105012\":\"麦子店街道\",\"110105013\":\"团结湖街道\",\"110105014\":\"六里屯街道\",\"110105015\":\"八里庄街道\",\"110105016\":\"双井街道\",\"110105017\":\"劲松街道\",\"110105018\":\"潘家园街道\",\"110105019\":\"垡头街道\",\"110105021\":\"南磨房地区\",\"110105022\":\"高碑店地区\",\"110105023\":\"将台地区\",\"110105024\":\"太阳宫地区\",\"110105025\":\"大屯街道\",\"110105026\":\"望京街道\",\"110105027\":\"小红门地区\",\"110105028\":\"十八里店地区\",\"110105029\":\"平房地区\",\"110105030\":\"东风地区\",\"110105031\":\"奥运村街道\",\"110105032\":\"来广营地区\",\"110105033\":\"常营地区\",\"110105034\":\"三间房地区\",\"110105035\":\"管庄地区\",\"110105036\":\"金盏地区\",\"110105037\":\"孙河地区\",\"110105038\":\"崔各庄地区\",\"110105039\":\"东坝地区\",\"110105040\":\"黑庄户地区\",\"110105041\":\"豆各庄地区\",\"110105042\":\"王四营地区\",\"110105043\":\"东湖街道\",\"110105400\":\"首都机场街道\"}";
		
		json = json.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\"", "");
		System.out.println(json);
	}
}
