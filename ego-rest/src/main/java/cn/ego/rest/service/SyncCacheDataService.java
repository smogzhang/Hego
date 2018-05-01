package cn.ego.rest.service;

import cn.ego.bean.EgoResult;

public interface SyncCacheDataService {

	EgoResult sync(String hkey);
	
	EgoResult sync(String hkey, String skey);

}
