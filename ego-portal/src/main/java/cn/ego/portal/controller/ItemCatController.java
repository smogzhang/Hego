package cn.ego.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ego.bean.ItemCatResult;
import cn.ego.portal.service.PorItemCatService;

/**
 * 商品分类展示
 * @author Sully
 *
 */
@RequestMapping("/portal/itemcat")
@Controller
public class ItemCatController {

	@Autowired
	private PorItemCatService porItemCatService;
	
	@RequestMapping("/list")
	@ResponseBody
	public ItemCatResult listItemCat() {
		ItemCatResult result = null;
		try {
			result = porItemCatService.queryAllItemCats();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
