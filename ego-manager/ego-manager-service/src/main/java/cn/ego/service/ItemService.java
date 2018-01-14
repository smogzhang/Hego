package cn.ego.service;

import cn.ego.bean.EUDataGridResult;
import cn.ego.pojo.TbItem;

public interface ItemService {
	
	//根据id查询单个商品 用于测试
	TbItem getItemById(Long itemId);
	
	//查询所有商品数据/item/list 异步请求
	EUDataGridResult listItem(int page, int rows);

}
