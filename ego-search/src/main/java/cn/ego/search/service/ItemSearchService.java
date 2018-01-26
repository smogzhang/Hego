package cn.ego.search.service;

import cn.ego.search.bean.SearchResult;

public interface ItemSearchService {

	SearchResult listItems(String queryString, Integer page, Integer rows) throws Exception;

}
