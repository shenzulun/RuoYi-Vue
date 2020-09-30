/**
 * File Name: PbcAPIController.java
 * Date: 2020-09-29 11:04:52
 */
package com.hyrcb.hydp.modules.pbc.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hyrcb.hydp.common.utils.CommonUtils;
import com.hyrcb.hydp.modules.pbc.model.CommonRequestModel;
import com.hyrcb.hydp.modules.pbc.model._001._001ResponseModel;
import com.hyrcb.hydp.modules.pbc.model._002._002ResponseModel;
import com.hyrcb.hydp.modules.pbc.model._003._003ResponseModel;
import com.hyrcb.hydp.modules.pbc.model._006._006ResponseModel;
import com.hyrcb.hydp.modules.pbc.model._007._007ResponseItem;
import com.hyrcb.hydp.modules.pbc.model._007._007ResponseModel;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import me.belucky.easytool.util.StringUtils;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-09-29
 * @version 1.0
 */
@RestController
@RequestMapping("/pbc/api/sst" )
public class PbcAPIController extends BaseController{
	
	
	/**
	 * 各类清单企业数量
	 * @param requestModel
	 * @return
	 */
	@RequestMapping("/getCustAnalyse" )
	public AjaxResult getCustAnalyse(@ModelAttribute CommonRequestModel requestModel) {
		List<Record> list = null;
		if(requestModel != null && StringUtils.isNotNull(requestModel.getCustType())) {
			list = Db.find("select \r\n" + 
					"t1.tag,\r\n" + 
					"sum(if(t2.cust_no is null,0,1)) as yrzxq,    -- 有融资需求\r\n" + 
					"sum(if(t2.cust_no is not null,0,1)) as wrzxq   -- 无融资需求\r\n" + 
					"from pbc_custinfo t1 left join (select distinct cust_no from pbc_loan_demand)t2 on t2.cust_no=t1.cust_no where t1.tag=? \r\n" + 
					"group by t1.tag", requestModel.getCustType());
		}else {
			list = Db.find("select \r\n" + 
					"'企业总数'  as tag,\r\n" + 
					"sum(if(t2.cust_no is null,0,1)) as yrzxq,    -- 有融资需求\r\n" + 
					"sum(if(t2.cust_no is not null,0,1)) as wrzxq   -- 无融资需求\r\n" + 
					"from pbc_custinfo t1 left join (select distinct cust_no from pbc_loan_demand)t2 on t2.cust_no=t1.cust_no\r\n" + 
					"union all\r\n" + 
					"select \r\n" + 
					"t1.tag,\r\n" + 
					"sum(if(t2.cust_no is null,0,1)) as yrzxq,    -- 有融资需求\r\n" + 
					"sum(if(t2.cust_no is not null,0,1)) as wrzxq   -- 无融资需求\r\n" + 
					"from pbc_custinfo t1 left join (select distinct cust_no from pbc_loan_demand)t2 on t2.cust_no=t1.cust_no\r\n" + 
					"group by t1.tag\r\n" + 
					"");
		}
		int length = list.size();
		
		String[] xAxis = new String[length];
		int[] data_y = new int[length];
		int[] data_w = new int[length];
		for(int i=0;i<length;i++) {
			Record r = list.get(i);
			xAxis[i] = r.getStr("tag");
			data_y[i] = CommonUtils.getInt(r, "yrzxq");
			data_w[i] = CommonUtils.getInt(r, "wrzxq");
		}
		_001ResponseModel resp = new _001ResponseModel(xAxis,data_y,data_w);
		return AjaxResult.success(resp);
	}
	
	/**
	 * 惠企政策条目数
	 * @param requestModel
	 * @return
	 */
	@RequestMapping("/getHqzctms")
	public AjaxResult getHqzctms(@ModelAttribute CommonRequestModel requestModel) {
		List<Record> list = Db.find("select  \r\n" + 
				"t2.dept_name,t1.cnt\r\n" + 
				"from (\r\n" + 
				"	select \r\n" + 
				"	dept_id,\r\n" + 
				"	count(1) as cnt\r\n" + 
				"	from pbc_article where article_type='ZCXX' group by dept_id\r\n" + 
				")t1,sys_dept t2 where t1.dept_id=t2.dept_id\r\n" + 
				"");
		int length = list.size();
		
		String[] xAxis = new String[length];
		int[] data = new int[length];
		for(int i=0;i<length;i++) {
			Record r = list.get(i);
			xAxis[i] = r.getStr("dept_name");
			data[i] = CommonUtils.getInt(r, "cnt");
		}
		_002ResponseModel resp = new _002ResponseModel(xAxis,data);
		return AjaxResult.success(resp);
	}
	
	/**
	 * 企业融资情况
	 * @param requestModel
	 * @return
	 */
	@RequestMapping("/getQyrzqk")
	public AjaxResult getQyrzqk(@ModelAttribute CommonRequestModel requestModel) {
		List<Record> list = null;
		if(requestModel != null && StringUtils.isNotNull(requestModel.getCustType())) {
			list = Db.find("select \r\n" + 
					"sum(if(t1.solve_status = '1',1,0)) as yrz,\r\n" + 
					"sum(if(t1.solve_status = '2',1,0)) as zzdj,\r\n" + 
					"sum(if(t1.solve_status = '3',1,0)) as wdj\r\n" + 
					"from pbc_loan_demand t1 where t1.cust_no in (select t2.cust_no from pbc_custinfo t2 where t2.tag=?)", requestModel.getCustType());
		}else {
			list = Db.find("select \r\n" + 
					"sum(if(t1.solve_status = '1',1,0)) as yrz,\r\n" + 
					"sum(if(t1.solve_status = '2',1,0)) as zzdj,\r\n" + 
					"sum(if(t1.solve_status = '3',1,0)) as wdj\r\n" + 
					"from pbc_loan_demand t1");
		}
		
		Record r = list.get(0);
		_003ResponseModel resp = new _003ResponseModel(CommonUtils.getInt(r,"zzdj"),CommonUtils.getInt(r, "yrz"),CommonUtils.getInt(r, "wdj"));
		return AjaxResult.success(resp);
	}
	
	/**
	 * 分区域融资对接情况
	 * @param requestModel
	 * @return
	 */
	@RequestMapping("/getFqyrzdjqk")
	public AjaxResult getFqyrzdjqk(@ModelAttribute CommonRequestModel requestModel) {
		
		return AjaxResult.success();
	}
	
	/**
	 * 企业融资覆盖情况
	 * @param requestModel
	 * @return
	 */
	@RequestMapping("/getQyrzfgqk")
	public AjaxResult getQyrzfgqk(@ModelAttribute CommonRequestModel requestModel) {
		
		return AjaxResult.success();
	}
	
	/**
	 * 分机构融资对接情况
	 * @param requestModel
	 * @return
	 */
	@RequestMapping("/getFjgrzdjqk")
	public AjaxResult getFjgrzdjqk(@ModelAttribute CommonRequestModel requestModel) {
		List<Record> list = null;
		if(requestModel != null && StringUtils.isNotNull(requestModel.getCustType())) {
			list = Db.find("select t3.dept_name,count(t1.demand_no) as cnt from pbc_loan_demand t1,pbc_bankinfo t2,sys_dept t3,pbc_custinfo t4\r\n" + 
					"where t1.solve_bank_no=t2.bank_id and t2.dept_id=t3.dept_id and t1.cust_no=t4.cust_no and t4.tag=? \r\n" + 
					"group by t3.dept_name\r\n" + 
					"order by count(t1.demand_no) desc", 
					requestModel.getCustType());
		}else {
			list = Db.find("select t3.dept_name,count(t1.demand_no) as cnt from pbc_loan_demand t1,pbc_bankinfo t2,sys_dept t3 \r\n" + 
					"where t1.solve_bank_no=t2.bank_id and t2.dept_id=t3.dept_id\r\n" + 
					"group by t3.dept_name\r\n" + 
					"order by count(t1.demand_no) desc");
		}
		List<String> xAxis = new ArrayList<>();
		List<Integer> data = new ArrayList<>();
		if(list != null && list.size() > 0) {
			for(Record r : list) {
				xAxis.add(r.getStr("dept_name"));
				data.add(r.getInt("cnt"));
			}
		}
		_006ResponseModel resp = new _006ResponseModel(xAxis, data);
		return AjaxResult.success(resp);
	}

	
	/**
	 * 获取企业的地图坐标信息
	 * @param requestModel
	 * @return
	 */
	@RequestMapping("/getQydtzb")
	public AjaxResult getQydtzb(@ModelAttribute CommonRequestModel requestModel) {
		List<Record> list = null;
		if(requestModel != null && StringUtils.isNotNull(requestModel.getCustType())) {
			list = Db.find("select t1.cust_no,t1.cust_name,t1.geo_code,t2.solve_status from pbc_custinfo t1,pbc_loan_demand t2 where t1.cust_no = t2.cust_no and t1.tag=?", 
					requestModel.getCustType());
		}else {
			list = Db.find("select t1.cust_no,t1.cust_name,t1.geo_code,t2.solve_status from pbc_custinfo t1,pbc_loan_demand t2 where t1.cust_no = t2.cust_no");
		}
		
		List<_007ResponseItem> data_yrz = new ArrayList<>();
		List<_007ResponseItem> data_zzdj = new ArrayList<>();
		List<_007ResponseItem> data_wdj = new ArrayList<>();
		if(list != null && list.size() > 0) {
			for(Record r : list) {
				String custName = r.getStr("cust_name");
				String solveStatus = r.getStr("solve_status");
				String geoCode = r.getStr("geo_code");
				if(StringUtils.isBlank(geoCode)) {
					continue;
				}
				String[] arr = geoCode.split(",");
				double[] douArr = {Double.parseDouble(arr[0]), Double.parseDouble(arr[1]), 1};
				
				_007ResponseItem item = new _007ResponseItem(custName, douArr);
				if("1".equals(solveStatus)) {
					//已融资
					data_yrz.add(item);
				}else if("2".equals(solveStatus)) {
					//正在对接
					data_zzdj.add(item);
				}else if("3".equals(solveStatus)) {
					//未对接
					data_wdj.add(item);
				}
			}
		}
		_007ResponseModel resp = new _007ResponseModel(data_yrz,data_zzdj,data_wdj);
		return AjaxResult.success(resp);
	}
	
}
