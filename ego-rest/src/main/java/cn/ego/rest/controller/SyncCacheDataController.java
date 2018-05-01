package cn.ego.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ego.bean.EgoResult;
import cn.ego.rest.service.SyncCacheDataService;

/**
 * 
 * 同步缓存数据：
 * 发布服务，接收key用于删除Redis中的数据
 * 使用时间：
 * 后台管理系统修修改数据时就调用该服务
 * 
 * @author Sully
 *
 */

@Controller
@RequestMapping("/cache/data")
public class SyncCacheDataController {

	@Autowired
	private SyncCacheDataService syncCacheDataService;
	
	/*
	 * 返回值暂不处理(返回null)
	 */
	
	@ResponseBody
	@RequestMapping("/sync")
	public EgoResult syncCacheDataByKey(@RequestParam String hkey, @RequestParam String skey) {
		// 删除整个键值
		if(skey == null){
			return syncCacheDataService.sync(hkey);
		}	
		// 删除哈希表中的某一列
		return syncCacheDataService.sync(hkey, skey);
	}
}
