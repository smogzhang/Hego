package cn.ego.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ego.bean.ItemCatResult;
import cn.ego.mapper.TbItemCatMapper;
import cn.ego.pojo.TbItemCat;
import cn.ego.pojo.TbItemCatExample;
import cn.ego.pojo.TbItemCatExample.Criteria;
import cn.ego.rest.bean.ItemCat;
import cn.ego.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public ItemCatResult queryAllItemCats() {
		ItemCatResult itemCatResult = new ItemCatResult();
		itemCatResult.setData(listChild(0l));
		return itemCatResult;
	}

	private List<Object> listChild(long parentId) {
		List<Object> itemCats = new ArrayList<>();
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		ItemCat cat = null;
		for (TbItemCat tbItemCat : list) {
			if(tbItemCat.getIsParent()) {
				//父节点
				cat = new ItemCat();
				cat.setUrl("/category/" + tbItemCat.getId() + ".html");
				cat.setName(tbItemCat.getName());
				cat.setItem(listChild(tbItemCat.getId()));
				itemCats.add(cat);
			} else {
				//子节点
				String catItem = "/item/" + tbItemCat.getId() + ".html|" + tbItemCat.getName();
				itemCats.add(catItem);
			}
		}
		return itemCats;
	}
}
