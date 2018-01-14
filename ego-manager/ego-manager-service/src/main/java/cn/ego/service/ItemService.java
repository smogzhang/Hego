package cn.ego.service;

import cn.ego.bean.EUDataGridResult;
import cn.ego.pojo.TbItem;

public interface ItemService {

	TbItem getItemById(Long itemId);

	EUDataGridResult listItem(int page, int rows);

}
