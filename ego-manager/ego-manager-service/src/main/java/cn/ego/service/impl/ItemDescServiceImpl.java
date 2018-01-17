package cn.ego.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ego.mapper.TbItemDescMapper;
import cn.ego.pojo.TbItemDesc;
import cn.ego.service.ItemDescService;

@Service
public class ItemDescServiceImpl implements ItemDescService {

	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	@Override
	public TbItemDesc getItemDescByItemId(Long itemId) {
		return itemDescMapper.selectByPrimaryKey(itemId);
	}

}
