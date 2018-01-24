package cn.ego.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.ego.portal.service.PorItemCatService;
import cn.ego.rest.bean.ItemCatResult;
import cn.ego.utils.HttpClientUtil;
import cn.ego.utils.JsonUtils;

@Service
public class PorItemCatServiceImpl implements PorItemCatService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_CATEGORY}")
	private String ITEM_CATEGORY;
	
	@Override
	public ItemCatResult queryAllItemCats() throws Exception {
		String jsonData = HttpClientUtil.doGet(REST_BASE_URL + ITEM_CATEGORY);
		return JsonUtils.jsonToPojo(jsonData, ItemCatResult.class);
	}

}
