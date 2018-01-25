package cn.ego.rest.service;

import java.util.List;

import cn.ego.rest.bean.ADItem;

public interface ContentService {

	List<ADItem> listAdvertising(long categoryId);
	
}
