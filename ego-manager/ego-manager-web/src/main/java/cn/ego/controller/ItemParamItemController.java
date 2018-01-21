package cn.ego.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ego.service.ItemParamItemService;

@Controller
public class ItemParamItemController {

	@Autowired
	private ItemParamItemService itemParamItemService;
	
	@RequestMapping("/showitem/{itemid}")
	public String getParamItemById(@PathVariable long itemid, Model model) {
		String itemParam = itemParamItemService.getParamItemById(itemid);
		model.addAttribute("itemParam", itemParam);
		return "item";
	}
	
}
