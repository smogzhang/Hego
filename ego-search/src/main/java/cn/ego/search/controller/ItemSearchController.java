package cn.ego.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ego.bean.search.SearchResult;
import cn.ego.search.service.ItemSearchService;

@RequestMapping("/search/item")
@Controller
public class ItemSearchController {
	
	@Autowired
	private ItemSearchService itemSearchService;

	@RequestMapping("/list")
	@ResponseBody
	public SearchResult listItemsBySolrCore(@RequestParam(value="kw",required=false) String queryString, @RequestParam(defaultValue="1") int page, @RequestParam(defaultValue="20") int rows) {
		SearchResult result = null;
		try {
			queryString = new String(queryString.getBytes("ISO8859-1"),"UTF-8");
			result = itemSearchService.listItems(queryString, page, rows);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}