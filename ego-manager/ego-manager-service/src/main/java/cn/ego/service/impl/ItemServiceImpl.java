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
import cn.ego.mapper.TbItemParamItemMapper;
import cn.ego.pojo.TbItem;
import cn.ego.pojo.TbItemDesc;
import cn.ego.pojo.TbItemExample;
import cn.ego.pojo.TbItemParamItem;
import cn.ego.service.ItemService;
import cn.ego.utils.IDUtils;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

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
	public void saveItemWithDesc(TbItem item, String desc, String itemParams) {

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
		
		//更新：接收商品规格参数，并保存
		//[{"group":"多媒体分组1","params":[{"k":"值1","v":"值11"},{"k":"值2","v":"值21"}]}]
		TbItemParamItem itemParamItem = new TbItemParamItem();
//		itemParamItem.setId(id); 数据库设置为自动更新
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParams);
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);
		itemParamItemMapper.insert(itemParamItem);
	}

	@Override
	public void updateItemWithDesc(TbItem item, String desc) throws Exception {
		//更新时间
		Date updateDate = new Date();
		
		TbItem originalItem = itemMapper.selectByPrimaryKey(item.getId());
		originalItem.setBarcode(item.getBarcode());
		originalItem.setCid(item.getCid());
		originalItem.setImage(item.getImage());
		originalItem.setNum(item.getNum());
		originalItem.setPrice(item.getPrice());
		originalItem.setSellPoint(item.getSellPoint());
		originalItem.setTitle(item.getTitle());
		originalItem.setUpdated(updateDate);
		itemMapper.updateByPrimaryKey(originalItem);
		
		TbItemDesc originalItemDesc = itemDescMapper.selectByPrimaryKey(item.getId());
		originalItemDesc.setItemDesc(desc);
		originalItemDesc.setUpdated(updateDate);
		itemDescMapper.updateByPrimaryKey(originalItemDesc);
	}

	@Override
	public void updateReshelfItemById(long itemId) throws Exception {
		changeItemStatus(itemId, "reshelf");
	}

	@Override
	public void updateInstockItemById(long itemId) throws Exception {
		changeItemStatus(itemId, "instock");
	}

	//删除商品，至status为3 或者 直接删除数据库记录？
	@Override
	public void deleteItemById(long itemId) throws Exception {
		changeItemStatus(itemId, "delete");
	}
	
	private void changeItemStatus(long itemId, String status) throws Exception {
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		item.setUpdated(new Date());
		// 商品状态，1-正常，2-下架，3-删除
		if("delete".equals(status)) {
			item.setStatus((byte) 3);
		} else if ("instock".equals(status)) {
			item.setStatus((byte) 2);
		} else if ("reshelf".equals(status)) {
			item.setStatus((byte) 1);
		}
		itemMapper.updateByPrimaryKey(item);
	}
	
}
