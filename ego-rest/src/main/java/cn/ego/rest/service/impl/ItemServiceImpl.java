package cn.ego.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ego.mapper.TbItemMapper;
import cn.ego.pojo.TbItem;
import cn.ego.rest.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Override
	public TbItem getItemById(long itemId) throws Exception {
		return itemMapper.selectByPrimaryKey(itemId);
	}

}
