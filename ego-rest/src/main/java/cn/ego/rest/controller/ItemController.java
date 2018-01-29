package cn.ego.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ego.bean.EgoResult;
import cn.ego.pojo.TbItem;
import cn.ego.rest.service.ItemService;

@Controller
@RequestMapping("/rest")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public EgoResult getItemById(@PathVariable long itemId) {
		TbItem item = null;
		try {
			item = itemService.getItemById(itemId);
			return EgoResult.ok(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EgoResult.build(400, "");
	}
	
}
