package cn.ego.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.ego.bean.EgoResult;
import cn.ego.bean.search.SearchResult;
import cn.ego.portal.service.SearchService;
import cn.ego.utils.HttpClientUtil;
import cn.ego.utils.JsonUtils;

@Service
public class SearchServiceImpl implements SearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	@Value("${ITEM_LIST}")
	private String ITEM_LIST;
	
	@Override
	public EgoResult listItemsByQueryString(String queryString, Integer page) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("kw", queryString);
		param.put("page", page == null ? "1" : page + "");
		String jsonResult = HttpClientUtil.doGet(SEARCH_BASE_URL + ITEM_LIST, param);
		return EgoResult.ok(JsonUtils.jsonToPojo(jsonResult, SearchResult.class));
	}

}
