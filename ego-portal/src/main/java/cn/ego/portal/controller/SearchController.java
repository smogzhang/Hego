package cn.ego.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.ego.bean.EgoResult;
import cn.ego.bean.search.SearchResult;
import cn.ego.portal.service.SearchService;

@Controller
@RequestMapping("/portal/search")
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/items")
	public String searchItems(@RequestParam(value="q", required=false) String queryString, Integer page, Model model) {
		EgoResult result = searchService.listItemsByQueryString(queryString, page);
		SearchResult searchResult = (SearchResult) result.getData();
		if(searchResult == null) {
			return "error/exception";
		}
		model.addAttribute("itemList", searchResult.getItemList());
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", searchResult.getPageCount());
		model.addAttribute("page", searchResult.getCurPage());
		model.addAttribute("pages", searchResult.getPageCount());
		return "search";
	}
	
}
