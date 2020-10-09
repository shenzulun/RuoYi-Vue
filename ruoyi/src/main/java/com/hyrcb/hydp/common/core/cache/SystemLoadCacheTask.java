/**
 * File Name: SystemLoadCacheTask.java
 * Date: 2020-09-16 14:53:07
 */
package com.hyrcb.hydp.common.core.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.hyrcb.hydp.common.config.StaticCode;
import com.hyrcb.hydp.common.core.dataAuth.DataAuth;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import me.belucky.easytool.task.AbstractTask;
import me.belucky.easytool.util.CacheUtils;

/**
 * Description: 系统初始化缓存任务
 * @author shenzulun
 * @date 2020-09-16
 * @version 1.0
 */
public class SystemLoadCacheTask extends AbstractTask{

	public SystemLoadCacheTask(String taskName) {
		super(taskName);
	}

	public void execute() {
		authCache();
		dictCache();
	}
	
	/**
	 * 权限缓存
	 */
	public void authCache() {
		Map<String, DataAuth> map = new ConcurrentHashMap<>();
		Map<String, List<DataAuth>> map1 = new ConcurrentHashMap<>();
		List<Record> dataAuthList = Db.find("select t1.role_id, t1.mapper_name,t3.data_scope,t1.dept_alias from sys_data_auth t1,sys_role t3\r\n" + 
				"where 1=1 and  t1.role_id=t3.role_id \r\n" + 
				"and t3.del_flag=0\r\n" + 
				"");
		if(dataAuthList != null && dataAuthList.size() > 0) {
			for(Record r : dataAuthList) {
				Long roleId = r.getLong("role_id");
				String mapperName = r.getStr("mapper_name");
				DataAuth da = new DataAuth(roleId, mapperName, r.getStr("data_scope"), r.getStr("dept_alias"));
				map.put(mapperName + "-" + roleId, da);
				
				List<DataAuth> list = map1.get(mapperName);
				if(list == null) {
					list = new ArrayList<DataAuth>();
				}
				list.add(da);
				map1.put(mapperName, list);
			}
		}
		CacheUtils.putCache(StaticCode.DATA_AUTH_MAP, map);
		CacheUtils.putCache(StaticCode.DATA_AUTH_MAP_MAPPERNAME, map1);
		log.info("数据权限配置表缓存完毕...");
	}
	
	/**
	 * 字典缓存
	 */
	public void dictCache() {
		List<Record> list = Db.find("select * from sys_dict_tree");
		ConcurrentMap<String, Record> map = new ConcurrentHashMap<>();
		if(list != null && list.size() > 0) {
			for(Record r : list) {
				String key = r.getStr("type") + "-" + r.getStr("code");
				map.put(key, r);
			}
		}
		CacheUtils.putCache(StaticCode.CACHE_DICT_TREE_MAP, map);
		log.info("字典数表缓存完毕...");
	}

}
