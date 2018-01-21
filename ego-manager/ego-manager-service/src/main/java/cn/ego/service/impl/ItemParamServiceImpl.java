package cn.ego.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ego.bean.EUDataGridResult;
import cn.ego.bean.EgoResult;
import cn.ego.mapper.TbItemCatMapper;
import cn.ego.mapper.TbItemParamMapper;
import cn.ego.pojo.TbItemParam;
import cn.ego.pojo.TbItemParamExample;
import cn.ego.pojo.TbItemParamExample.Criteria;
import cn.ego.service.ItemParamService;
import cn.ego.vo.ItemParamDataVo;

@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper itemParamMapper;
	
	@Autowired TbItemCatMapper itemCatMapper;
	
	@Override
	public EgoResult getItemParamByItemCatId(long itemCatId) throws Exception{
		
		TbItemParamExample itemParamExample = new TbItemParamExample();
		Criteria criteria = itemParamExample.createCriteria();
		criteria.andItemCatIdEqualTo(itemCatId);
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(itemParamExample);
		if(list != null && list.size() > 0) {
			return EgoResult.ok(list.get(0));
		}
		//查询成功，但没有数据
		return EgoResult.ok();
	}

	@Override
	public EgoResult saveItemParamWithItemCatId(long itemCatId, String paramData) throws Exception {
		Date date = new Date();
		TbItemParam record = new TbItemParam();
		//record.setId(IDUtils.genItemId()); 数据库自动递增，可以不用设置
		record.setItemCatId(itemCatId);
		record.setParamData(paramData);
		record.setCreated(date);
		record.setUpdated(date);
		itemParamMapper.insert(record);
		return EgoResult.ok();
	}

	@Override
	public EUDataGridResult listItemParamByPage(int page, int rows) {
//		TbItemParamExample example = new TbItemParamExample();
//		Criteria criteria = example.createCriteria();
		PageHelper.startPage(page, rows);
		List<ItemParamDataVo> list = itemParamMapper.selectItemParamWithItemCatName();
		PageInfo<ItemParamDataVo> pageList = new PageInfo<ItemParamDataVo>(list);
		
		EUDataGridResult result = new EUDataGridResult();
		result.setTotal(pageList.getTotal());
		result.setRows(list);
		return result;
	}

	@Override
	public EgoResult deleteItemParamById(long itemParamId) {
		int i = itemParamMapper.deleteByPrimaryKey(itemParamId);
		if(i > 0) {
			return EgoResult.ok();
		}
		return EgoResult.build(1, "删除失败!");
	}

}