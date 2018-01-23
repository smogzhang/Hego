package cn.ego.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ego.bean.EUDataGridResult;
import cn.ego.bean.EgoResult;
import cn.ego.service.ItemParamService;
/**
 * 商品参数模板管理
 * @author Sully
 *
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {

	@Autowired ItemParamService itemParamService;
	
	/**
	 * 查询商品规格模板表，ps：与商品类目表一一对应
	 * @param itemCatId
	 * @return
	 */
	@RequestMapping("/query/itemcatid/{itemCatId}")
	@ResponseBody
	public EgoResult queryItemParamByItemCatId(@PathVariable("itemCatId") long itemCatId) {
		System.out.println("传递商品类目id：" + itemCatId);
		try {
			return itemParamService.getItemParamByItemCatId(itemCatId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EgoResult.ok();
	}
	/**
	 * 存储商品规格模板tb_item_param
	 * @param itemCatId 商品类目id
	 * @param paramData 商品参数规格模板
	 * @return
	 */
	@RequestMapping("/save/{itemCatId}")
	@ResponseBody
	public EgoResult saveItemParamWithItemCatId(@PathVariable("itemCatId") long itemCatId, String paramData) {
		try {
			EgoResult result = itemParamService.saveItemParamWithItemCatId(itemCatId, paramData);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EgoResult.build(1, "新增商品规格失败");
	}
	
	/**
	 * 分页展示商品规格参数模板
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EUDataGridResult listItemParam(@RequestParam(value="page", defaultValue="1")int page, @RequestParam(value="rows", defaultValue="30")int rows) {
		return itemParamService.listItemParamByPage(page, rows);
	}
	/**
	 * 删除商品规格模板
	 * @param ItemParamId
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public EgoResult deleteItemParam(@RequestParam("ids") long ItemParamId) {
		return itemParamService.deleteItemParamById(ItemParamId);
	}
	
}