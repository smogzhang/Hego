package cn.ego.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ego.bean.EgoResult;
import cn.ego.jedis.JedisClient;
import cn.ego.rest.service.SyncCacheDataService;

@Service
public class SyncCacheDataServiceImpl implements SyncCacheDataService {

	@Autowired
	private JedisClient jedisCluster;

	@Override
	public EgoResult sync(String hkey) {
		jedisCluster.del(hkey);
		return null;
	}

	@Override
	public EgoResult sync(String hkey, String skey) {
		jedisCluster.hdel(hkey, skey);
		return null;
	}
	

}
