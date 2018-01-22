package cn.ego.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ego.rest.bean.ItemCatResult;
import cn.ego.rest.service.ItemCatService;

@Controller
@RequestMapping("/rest/item")
public class ItemCatController {
//http://localhost:8081/rest/item/all?callback=category.getDataService
	
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/all")
	@ResponseBody
	public MappingJacksonValue queryAllItemCats(String callback) {
		ItemCatResult result = itemCatService.queryAllItemCats();
		MappingJacksonValue value = new MappingJacksonValue(result);
		value.setJsonpFunction(callback);
		return value;
	}
	
}
