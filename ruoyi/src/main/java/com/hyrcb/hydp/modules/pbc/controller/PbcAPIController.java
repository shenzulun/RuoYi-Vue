/**
 * File Name: PbcAPIController.java
 * Date: 2020-09-29 11:04:52
 */
package com.hyrcb.hydp.modules.pbc.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hyrcb.hydp.common.config.StaticCode;
import com.hyrcb.hydp.common.utils.CommonUtils;
import com.hyrcb.hydp.modules.pbc.model.CommonRequestModel;
import com.hyrcb.hydp.modules.pbc.model._001._001ResponseModel;
import com.hyrcb.hydp.modules.pbc.model._002._002ResponseModel;
import com.hyrcb.hydp.modules.pbc.model._003._003ResponseModel;
import com.hyrcb.hydp.modules.pbc.model._004._004ResponseModel;
import com.hyrcb.hydp.modules.pbc.model._005._005ResponseModel;
import com.hyrcb.hydp.modules.pbc.model._006._006ResponseModel;
import com.hyrcb.hydp.modules.pbc.model._007._007ResponseItem;
import com.hyrcb.hydp.modules.pbc.model._007._007ResponseModel;
import com.hyrcb.hydp.modules.pbc.model._008._008ResponseModel;
import com.hyrcb.hydp.modules.pbc.model._009._009ResponseModel;
import com.hyrcb.hydp.modules.pbc.model._010._010ResponseModel;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import me.belucky.easytool.util.CacheUtils;
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
	public static final int QUERY_TYPE_DEFUALT = 0;
	/**
	 * 客户信息子查询
	 */
	public static final int QUERY_TYPE_CUSTINFO = 1;
	
	
	/**
	 * 各类清单企业数量
	 * @param requestModel
	 * @return
	 */
	@RequestMapping("/getCustAnalyse" )
	public AjaxResult getCustAnalyse(@ModelAttribute CommonRequestModel requestModel) {
		
		StringBuffer baseSql = new StringBuffer();
		List<String> whereList = new ArrayList<>();		
		generateCond(requestModel, baseSql, whereList, "t1", QUERY_TYPE_DEFUALT);
		
		List<Record> list = null;
		String custType = requestModel.getCustType();
		if("企业总数".equals(custType)) {
			custType = null;
		}
		if(requestModel != null && StringUtils.isNotNull(custType)) {
			list = Db.find("select \r\n" + 
					"t1.tag,\r\n" + 
					"sum(if(t2.cust_no is null,0,1)) as yrzxq,    -- 有融资需求\r\n" + 
					"sum(if(t2.cust_no is not null,0,1)) as wrzxq   -- 无融资需求\r\n" + 
					"from pbc_custinfo t1 left join (select distinct cust_no from pbc_loan_demand)t2 on t2.cust_no=t1.cust_no where 1=1  \r\n" + 
					baseSql.toString() + 
					" group by t1.tag", whereList.toArray());
		}else {
			List<String> whereList1 = new ArrayList<>();	
			whereList1.addAll(whereList);
			whereList1.addAll(whereList);
			list = Db.find("select \r\n" + 
					"'企业总数'  as tag,\r\n" + 
					"sum(if(t2.cust_no is null,0,1)) as yrzxq,    -- 有融资需求\r\n" + 
					"sum(if(t2.cust_no is not null,0,1)) as wrzxq   -- 无融资需求\r\n" + 
					"from pbc_custinfo t1 left join (select distinct cust_no from pbc_loan_demand)t2 on t2.cust_no=t1.cust_no where 1=1 \r\n" + 
					baseSql.toString() + 
					"union all\r\n" + 
					"select \r\n" + 
					"t1.tag,\r\n" + 
					"sum(if(t2.cust_no is null,0,1)) as yrzxq,    -- 有融资需求\r\n" + 
					"sum(if(t2.cust_no is not null,0,1)) as wrzxq   -- 无融资需求\r\n" + 
					"from pbc_custinfo t1 left join (select distinct cust_no from pbc_loan_demand)t2 on t2.cust_no=t1.cust_no where 1=1 \r\n" + 
					baseSql.toString() + 
					"group by t1.tag order by (yrzxq + wrzxq) desc  \r\n" + 
					"", whereList1.toArray());
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
				")t1,sys_dept t2 where t1.dept_id=t2.dept_id order by t1.cnt desc limit 5 \r\n" + 
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
		
		StringBuffer baseSql = new StringBuffer("select \r\n" + 
				"sum(if(t1.solve_status = '1',1,0)) as yrz,\r\n" + 
				"sum(if(t1.solve_status = '2',1,0)) as zzdj,\r\n" + 
				"sum(if(t1.solve_status = '3',1,0)) as wdj\r\n" + 
				"from pbc_loan_demand t1 where 1=1 ");
		
		List<String> whereList = new ArrayList<>();
		
		StringBuffer endSql = new StringBuffer(" ");
		
		generateCond(requestModel, baseSql, whereList, "t1", QUERY_TYPE_CUSTINFO);
		String sql = baseSql.append(endSql).toString();
		List<Record> list = Db.find(sql, whereList.toArray());

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
		
		StringBuffer baseSql = new StringBuffer("select t1.address_code,\r\n" + 
				"sum(if(t2.solve_status = '1',1,0)) as ydj,\r\n" + 
				"sum(if(t2.solve_status = '3',1,0)) as wdj,\r\n" + 
				"sum(if(t2.solve_status = '2',1,0)) as zzdj\r\n" + 
				"from pbc_custinfo t1,pbc_loan_demand t2 where 1=1 and t1.cust_no = t2.cust_no and t1.address_code like '331003%' ");
		
		List<String> whereList = new ArrayList<>();
		
		StringBuffer endSql = new StringBuffer(" group by t1.address_code \r\n" + 
				"order by count(1) desc\r\n" + 
				"limit 5");
		
		generateCond(requestModel, baseSql, whereList, "t1", QUERY_TYPE_DEFUALT);
		String sql = baseSql.append(endSql).toString();
		List<Record> list = Db.find(sql, whereList.toArray());
		
		
		List<String> xAxis = new ArrayList<>();
		List<Integer> datay = new ArrayList<>();
		List<Integer> dataw = new ArrayList<>();
		List<Integer> data_zzdj = new ArrayList<>();
		if(list != null && list.size() > 0) {
			ConcurrentMap<String, Record> map = CacheUtils.getCache(StaticCode.CACHE_DICT_TREE_MAP);
			for(Record r : list) {
				String addressCode = r.getStr("address_code");
				xAxis.add(map.get("ADDRESS_CODE-" + addressCode).getStr("name"));
				datay.add(r.getInt("ydj"));
				dataw.add(r.getInt("wdj"));
				data_zzdj.add(r.getInt("zzdj"));
			}
		}
		_004ResponseModel resp = new _004ResponseModel(xAxis, datay, dataw, data_zzdj);
		return AjaxResult.success(resp);
	}
	
	/**
	 * 企业融资覆盖情况
	 * @param requestModel
	 * @return
	 */
	@RequestMapping("/getQyrzfgqk")
	public AjaxResult getQyrzfgqk(@ModelAttribute CommonRequestModel requestModel) {
		List<Record> list = new ArrayList<Record>();
		
		String custType = requestModel.getCustType();
		if("企业总数".equals(custType)) {
			custType = null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
		
		LocalDate date = LocalDate.now();
		List<String> dateList = new ArrayList<>();
		dateList.add(date.plusMonths(-9).format(formatter));
		dateList.add(date.plusMonths(-8).format(formatter));
		dateList.add(date.plusMonths(-7).format(formatter));
		dateList.add(date.plusMonths(-6).format(formatter));
		dateList.add(date.plusMonths(-5).format(formatter));
		dateList.add(date.plusMonths(-4).format(formatter));
		dateList.add(date.plusMonths(-3).format(formatter));
		dateList.add(date.plusMonths(-2).format(formatter));
		dateList.add(date.plusMonths(-1).format(formatter));
		dateList.add(date.plusMonths(0).format(formatter));
		
		for(String rq : dateList) {
			if(rq == null) {
				continue;
			}
			String rq1 = CommonUtils.getFirstDayOfNextMonth(rq, "yyyy-MM") + "-01";	
			StringBuffer baseSql = new StringBuffer("select '" + rq + "' as rq, \r\n" + 
					"sum(if(t.solve_status = '1',t.loan_amount,0)) / sum(t.loan_amount) as 'ljfgl',\r\n" + 
					"sum(if(t.solve_status = '1',t.loan_amount,0)) as 'ljcjje'\r\n" + 
					"from pbc_loan_demand t \r\n" + 
					"where t.create_time < ? ");
			
			List<String> whereList = new ArrayList<>();
			whereList.add(rq1);
			
			if(requestModel != null && StringUtils.isNotNull(custType)) {
				baseSql.append(" and t.cust_no in (select t2.cust_no from pbc_custinfo t2 where t2.tag=?) ");
				whereList.add(custType);
			}
			String addressCode = requestModel.getAddressCode();
			if(requestModel != null && StringUtils.isNotNull(addressCode)) {
				baseSql.append(" and t.cust_no in (select t2.cust_no from pbc_custinfo t2 where t2.address_code=?) ");
				whereList.add(addressCode);
			}
			
			List<Record> list1 = Db.find(baseSql.toString(), whereList.toArray());
			if(list1.get(0).get("ljfgl") == null) {
				continue;
			}
			list.addAll(list1);
		}
		
		List<String> xAxis = new ArrayList<>();
		List<Double> dataFg = new ArrayList<>();
		List<Double> dataCj = new ArrayList<>();
		if(list != null && list.size() > 0) {
			for(Record r : list) {
				xAxis.add(r.getStr("rq").replaceAll("-", "."));
				dataFg.add(r.getDouble("ljfgl"));
				dataCj.add(r.getDouble("ljcjje"));
			}
		}
		_005ResponseModel resp = new _005ResponseModel(xAxis, dataFg, dataCj);
		return AjaxResult.success(resp);
	}
	
	/**
	 * 分机构融资对接情况
	 * @param requestModel
	 * @return
	 */
	@RequestMapping("/getFjgrzdjqk")
	public AjaxResult getFjgrzdjqk(@ModelAttribute CommonRequestModel requestModel) {
		
		StringBuffer baseSql = new StringBuffer("select t3.dept_name,count(t1.demand_no) as cnt,sum(t1.loan_amount) as sumJe from pbc_loan_demand t1,pbc_bankinfo t2,sys_dept t3,pbc_custinfo t4 \r\n" + 
				"where t1.solve_bank_no=t2.bank_id and t2.dept_id=t3.dept_id and t1.cust_no=t4.cust_no ");
				
		List<String> whereList = new ArrayList<>();
		
		StringBuffer endSql = new StringBuffer(" group by t3.dept_name\r\n" + 
				"order by count(t1.demand_no) desc limit 5");
		
		generateCond(requestModel, baseSql, whereList, "t4", QUERY_TYPE_DEFUALT);
		String sql = baseSql.append(endSql).toString();
		List<Record> list = Db.find(sql, whereList.toArray());
		
		List<String> xAxis = new ArrayList<>();
		List<Double> data = new ArrayList<>();
		List<Integer> dataBs = new ArrayList<>();
		if(list != null && list.size() > 0) {
			for(Record r : list) {
				xAxis.add(r.getStr("dept_name"));
				data.add(r.getDouble("sumJe"));
				dataBs.add(r.getInt("cnt"));
			}
		}
		_006ResponseModel resp = new _006ResponseModel(xAxis, data, dataBs);
		return AjaxResult.success(resp);
	}

	
	/**
	 * 获取企业的地图坐标信息
	 * @param requestModel
	 * @return
	 */
	@RequestMapping("/getQydtzb")
	public AjaxResult getQydtzb(@ModelAttribute CommonRequestModel requestModel) {
		
		StringBuffer baseSql = new StringBuffer("select t1.cust_no,t1.cust_name,t1.geo_code,t2.solve_status from pbc_custinfo t1,pbc_loan_demand t2 where t1.cust_no = t2.cust_no ");
		
		List<String> whereList = new ArrayList<>();
		
		StringBuffer endSql = new StringBuffer("");
				
		generateCond(requestModel, baseSql, whereList, "t1", QUERY_TYPE_DEFUALT);
		String sql = baseSql.append(endSql).toString();
		List<Record> list = Db.find(sql, whereList.toArray());
		
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
	
	
	public void generateCond(CommonRequestModel requestModel, StringBuffer baseSql, List<String> whereList, String tableAlias, int queryType) {
		if(requestModel != null) {
			String custType = requestModel.getCustType();
			if("企业总数".equals(custType)) {
				custType = null;
			}
			if( StringUtils.isNotNull(custType)) {
				if(queryType == QUERY_TYPE_CUSTINFO) {
					baseSql.append(" and ").append(tableAlias).append(".cust_no in (select pc1.cust_no from pbc_custinfo pc1 where pc1.tag=?) ");
				}else {
					baseSql.append(" and ").append(tableAlias).append(".tag=? ");
				}
				whereList.add(custType);
			}
			
			String addressCode = requestModel.getAddressCode();
			if( StringUtils.isNotNull(addressCode)) {
				if(queryType == QUERY_TYPE_CUSTINFO) {
					baseSql.append(" and ").append(tableAlias).append(".cust_no in (select pc1.cust_no from pbc_custinfo pc1 where pc1.address_code=?) ");
				}else {
					baseSql.append(" and ").append(tableAlias).append(".address_code=? ");
				}
				whereList.add(addressCode);
			}
		}
	}
	
	/**
	 * 惠企政策列表查询
	 * @param requestModel
	 * @return
	 */
	@RequestMapping("/getHqzcList")
	public AjaxResult getHqzcList(@ModelAttribute CommonRequestModel requestModel) {
		List<_008ResponseModel> resp = new ArrayList<_008ResponseModel>();
		
		String sql = "select t1.id,t2.dept_name,t1.create_time,t1.title from pbc_article t1,sys_dept t2 \r\n" + 
				"where t1.dept_id=t2.dept_id and t1.article_type='ZCXX' \r\n" + 
				"order by t1.create_time desc\r\n" + 
				"";
		List<Record> list = Db.find(sql);	
		for(Record r : list) {
			_008ResponseModel m = new _008ResponseModel();
			int id = r.getInt("id");
			m.setId(id);
			m.setDept(r.getStr("dept_name"));
			m.setName(r.getStr("title"));
			m.setTime(r.getStr("create_time"));
			String url = "http://sst.hy96596.com/input/article/preview?id=" + id;
			m.setUrl(url);
			resp.add(m);
		}
		return AjaxResult.success(resp);
	}
	
	/**
	 * 银行特色产品
	 * @param requestModel
	 * @return
	 */
	@RequestMapping("/getProductList")
	public AjaxResult getProductList(@ModelAttribute CommonRequestModel requestModel) {
		List<_009ResponseModel> resp = new ArrayList<_009ResponseModel>();
		
		String sql = "select t1.id,t2.dept_name,t1.name,\r\n" + 
				"(select d.dict_label from sys_dict_data d where d.dict_type='LOAN_PRODUCT_TYPE' and d.dict_value=t1.product_type) as 'product_name' \r\n" + 
				"from pbc_loan_product t1,sys_dept t2 \r\n" + 
				"where t1.dept_id=t2.dept_id \r\n" + 
				"order by t1.create_time desc";
		List<Record> list = Db.find(sql);	
		for(Record r : list) {
			_009ResponseModel m = new _009ResponseModel();
			int id = r.getInt("id");
			m.setId(id);
			m.setBank(r.getStr("dept_name"));
			m.setName(r.getStr("name"));
			m.setRemark(r.getStr("product_name"));
			String url = "http://sst.hy96596.com/input/product/preview?id=" + id;
			m.setUrl(url);
			resp.add(m);
		}
		return AjaxResult.success(resp);
	}
	
	
	/**
	 * 三张清单
	 * @param requestModel
	 * @return
	 */
	@RequestMapping("/getSzqdList")
	public AjaxResult getSzqdList(@ModelAttribute CommonRequestModel requestModel) {
		List<_010ResponseModel> resp = new ArrayList<_010ResponseModel>();
		
		String sql = "select t1.id,t2.dept_name,t1.create_time,t1.title from pbc_article t1,sys_dept t2 \r\n" + 
				"where t1.dept_id=t2.dept_id and t1.article_type != 'ZCXX' \r\n" + 
				"order by t1.create_time desc\r\n" + 
				"";
		List<Record> list = Db.find(sql);	
		for(Record r : list) {
			_010ResponseModel m = new _010ResponseModel();
			int id = r.getInt("id");
			m.setId(id);
			m.setBank(r.getStr("dept_name"));
			m.setContent(r.getStr("title"));
			m.setTime(r.getStr("create_time"));
			String url = "http://sst.hy96596.com/input/article/preview?id=" + id;
			m.setUrl(url);
			resp.add(m);
		}
		return AjaxResult.success(resp);
	}
	
	
	
}
