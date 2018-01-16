package cn.ego.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ego.bean.EUDataGridResult;
import cn.ego.mapper.TbItemDescMapper;
import cn.ego.mapper.TbItemMapper;
import cn.ego.pojo.TbItem;
import cn.ego.pojo.TbItemDesc;
import cn.ego.pojo.TbItemExample;
import cn.ego.service.ItemService;
import cn.ego.utils.IDUtils;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;

	@Override
	public TbItem getItemById(Long itemId) {
		return itemMapper.selectByPrimaryKey(itemId);
	}

	@Override
	public EUDataGridResult listItem(int page, int rows) {
		// 分页
		PageHelper.startPage(page, rows);
		// 获取数据源
		TbItemExample example = new TbItemExample();
		List<TbItem> items = itemMapper.selectByExample(example);

		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(items);
		long total = pageInfo.getTotal();

		EUDataGridResult result = new EUDataGridResult();
		result.setRows(items);
		result.setTotal(total);
		return result;
	}

	@Override
	public void saveItemWithDesc(TbItem item, String desc) {

		long itemId = IDUtils.genItemId();
		Date date = new Date();
		item.setId(itemId);
		// 商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		item.setCreated(date);
		item.setUpdated(date);
		itemMapper.insert(item);

		// 创建商品描述对象
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		itemDescMapper.insert(itemDesc);
	}
}
