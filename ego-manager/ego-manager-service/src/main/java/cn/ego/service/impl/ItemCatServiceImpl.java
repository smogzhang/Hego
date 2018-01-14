package cn.ego.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ego.bean.EUTreeNode;
import cn.ego.mapper.TbItemCatMapper;
import cn.ego.pojo.TbItemCat;
import cn.ego.pojo.TbItemCatExample;
import cn.ego.pojo.TbItemCatExample.Criteria;
import cn.ego.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public List<EUTreeNode> listCatItem(long parentId) throws Exception {
		TbItemCatExample catExample = new TbItemCatExample();
		//设置查询条件
		Criteria criteria = catExample.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询条件
		List<TbItemCat> itemCats = itemCatMapper.selectByExample(catExample);
		//抽取想要条件
		List<EUTreeNode> nodes = new ArrayList<EUTreeNode>();
		EUTreeNode node = null;
		for (TbItemCat itemCat : itemCats) {
			node = new EUTreeNode();
			node.setId(itemCat.getId());
			node.setText(itemCat.getName());
			node.setState(itemCat.getIsParent() ? "closed" : "open");
			nodes.add(node);
		}
		return nodes;
	}

}
