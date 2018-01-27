package cn.ego.portal.service;

import cn.ego.bean.EgoResult;

public interface SearchService {

	EgoResult listItemsByQueryString(String queryString, Integer page);

}
