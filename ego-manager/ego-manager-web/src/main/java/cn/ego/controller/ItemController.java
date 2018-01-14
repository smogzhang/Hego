package cn.ego.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ego.bean.EUDataGridResult;
import cn.ego.pojo.TbItem;
import cn.ego.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	
	//根据id查询单个商品
	@RequestMapping("/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		return itemService.getItemById(itemId);
	}
	
	//查询所有商品数据/item/list 异步请求
	@RequestMapping("/list")
	@ResponseBody
	public EUDataGridResult listItem(@RequestParam(value="page",defaultValue="1") int page, int rows) {
		return itemService.listItem(page, rows);
	}
	
}
