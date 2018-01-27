package cn.ego.search.service;

import cn.ego.bean.search.SearchResult;

public interface ItemSearchService {

	SearchResult listItems(String queryString, Integer page, Integer rows) throws Exception;

}
