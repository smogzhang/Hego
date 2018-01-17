package cn.ego.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ego.bean.EgoResult;
import cn.ego.pojo.TbItem;
import cn.ego.pojo.TbItemDesc;
import cn.ego.service.ItemDescService;
import cn.ego.service.ItemService;
/**
 * rest 结构处理商品，包含：(新增)、编辑、删除、上架、下架
 * @author Sully
 *
 */
@RequestMapping("/rest/item")
@Controller
public class RestItemController {

	@Autowired
	private ItemDescService itemDescService;
	@Autowired
	private ItemService itemService;
	
	/**
	 * 用于获取商品描述(富文本)
	 * @param itemId 商品id
	 * @return
	 */
	@RequestMapping("/query/item/desc/{id}")
	@ResponseBody
	public EgoResult getItemDesc(@PathVariable("id") long itemId) {
		System.out.println("不含key值传递："+itemId);
		TbItemDesc itemDesc = itemDescService.getItemDescByItemId(itemId);
		return EgoResult.ok(itemDesc);
	}
	
	/**
	 * 更新商品信息表，连同商品描述表
	 * @param item 商品基本信息表，包含cid(商品类目)
	 * @param desc 商品描述表主题信息，id为商品主键id
	 * @return 
	 */
	@RequestMapping("/update")
	@ResponseBody
	public EgoResult updateItem(TbItem item, String desc) {
		try {
			itemService.updateItemWithDesc(item, desc);
			return EgoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EgoResult.build(1, "出错了，请重试");
	}
	
	//rest/item/delete
	@RequestMapping("/delete")
	@ResponseBody
	public EgoResult deleteItemById(@RequestParam("ids") long itemId) {
		try {
			itemService.deleteItemById(itemId);
			return EgoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EgoResult.build(1, "出错了，请重试");
	}
	
	/**
	 * 商品下架
	 * @param itemId 商品id
	 * @return
	 */
	@RequestMapping("/instock")
	@ResponseBody
	public EgoResult updateInstockItem(@RequestParam("ids") long itemId) {
		try {
			itemService.updateInstockItemById(itemId);
			return EgoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EgoResult.build(1, "出错了，请重试");
	}
	/**
	 * 商品重新上架
	 * @param itemId 商品id
	 * @return
	 */
	@RequestMapping("/reshelf")
	@ResponseBody
	public EgoResult updateReshelfItem(@RequestParam("ids") long itemId) {
		try {
			itemService.updateReshelfItemById(itemId);
			return EgoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EgoResult.build(1, "出错了，请重试");
	}
}
