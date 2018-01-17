package cn.ego.service;

import cn.ego.bean.EUDataGridResult;
import cn.ego.pojo.TbItem;

public interface ItemService {
	
	//根据id查询单个商品 用于测试
	TbItem getItemById(Long itemId);
	
	//查询所有商品数据/item/list 异步请求
	EUDataGridResult listItem(int page, int rows);

	//新增商品功能 item:商品表，desc：商品描述表，用商品id关联
	void saveItemWithDesc(TbItem item, String desc);

	//更新 item:商品表，desc：商品描述表，用商品id关联
	void updateItemWithDesc(TbItem item, String desc) throws Exception;

	//商品重新上架
	void updateReshelfItemById(long itemId) throws Exception;

	//商品下架
	void updateInstockItemById(long itemId) throws Exception;

	//删除商品
	void deleteItemById(long itemId) throws Exception;

}
