package cn.ego.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ego.service.ItemParamItemService;
/**
 * 商品参数管理
 * @author Sully
 *
 */
@Controller
public class ItemParamItemController {

	@Autowired
	private ItemParamItemService itemParamItemService;
	/**
	 * 商品规格参数查询
	 * @param itemid 根据商品id查询
	 * @param model
	 * @return 商品参数的json数据
	 */
	@RequestMapping("/showitem/{itemid}")
	public String getParamItemById(@PathVariable long itemid, Model model) {
		String itemParam = itemParamItemService.getParamItemById(itemid);
		model.addAttribute("itemParam", itemParam);
		return "item";
	}
	
}
