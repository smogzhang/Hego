package cn.ego.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ego.bean.EUTreeNode;
import cn.ego.service.ItemCatService;
/**
 * 商品分类管理
 * @author Sully
 *
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;

	// 查询所有商品分类，用于添加商品
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> listCatItem(@RequestParam(value="id",defaultValue="0") long parentId) {
		List<EUTreeNode> nodes = null;
		try {
			nodes = itemCatService.listCatItem(parentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodes;
	}
}
