package cn.ego.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ego.bean.ItemCatResult;
import cn.ego.rest.service.ItemCatService;
/**
 * 提供商品分类等查询服务
 * @author Sully
 *
 */
@Controller
@RequestMapping("/rest/itemcat")
public class ItemCatController {
//http://localhost:8081/rest/item/all?callback=category.getDataService
	
	@Autowired
	private ItemCatService itemCatService;
	
//	@RequestMapping("/all")
//	@ResponseBody
	/**
	 * js代码解决json跨域问题
	 * @param callback
	 * @return
	 */
//	public MappingJacksonValue queryAllItemCats(String callback) {
//		ItemCatResult result = itemCatService.queryAllItemCats();
//		MappingJacksonValue value = new MappingJacksonValue(result);
//		value.setJsonpFunction(callback);
//		return value;
//	}
	/**
	 * httpclient 解决跨域问题
	 * @return 直接返回json数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public ItemCatResult queryAllItemCategories() {
		return itemCatService.queryAllItemCats();
	}
	
}
