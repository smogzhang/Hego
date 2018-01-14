package cn.ego.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ego.bean.EUDataGridResult;
import cn.ego.mapper.TbItemMapper;
import cn.ego.pojo.TbItem;
import cn.ego.pojo.TbItemExample;
import cn.ego.pojo.TbItemExample.Criteria;
import cn.ego.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Override
	public TbItem getItemById(Long itemId) {
		return itemMapper.selectByPrimaryKey(itemId);
	}

	@Override
	public EUDataGridResult listItem(int page, int rows) {
		//分页
		PageHelper.startPage(page, rows);
		//获取数据源
		TbItemExample example = new TbItemExample();
		List<TbItem> items = itemMapper.selectByExample(example);
		
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(items);
		long total = pageInfo.getTotal();
		
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(items);
		result.setTotal(total);
		return result;
	}

}
