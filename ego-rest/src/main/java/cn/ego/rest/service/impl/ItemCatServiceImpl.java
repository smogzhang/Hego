package cn.ego.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.ego.bean.ItemCatResult;
import cn.ego.mapper.TbItemCatMapper;
import cn.ego.pojo.TbItemCat;
import cn.ego.pojo.TbItemCatExample;
import cn.ego.pojo.TbItemCatExample.Criteria;
import cn.ego.rest.bean.ItemCat;
import cn.ego.rest.dao.JedisClient;
import cn.ego.rest.service.ItemCatService;
import cn.ego.utils.JsonUtils;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Autowired
	private JedisClient jedisCluster;
	@Value("${ITEM_CATEGORY_IDENTIFYING}")
	private String ITEM_CATEGORY_IDENTIFYING;
	
	
	@Override
	public ItemCatResult queryAllItemCats() {
		ItemCatResult itemCatResult = null;
		//查询缓存
		try {
			String cacheData = jedisCluster.hget(ITEM_CATEGORY_IDENTIFYING, 0 + "");
			if(!StringUtils.isEmpty(cacheData)) {
				itemCatResult = new ItemCatResult();
				itemCatResult.setData(JsonUtils.jsonToList(cacheData, Object.class));
				return itemCatResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		itemCatResult = new ItemCatResult();
		List<Object> objects = listChild(0l);
		//添加缓存
		if(objects != null && objects.size() > 0) {
			jedisCluster.hset(ITEM_CATEGORY_IDENTIFYING, 0 + "", JsonUtils.objectToJson(objects));
		}
		itemCatResult.setData(objects);
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
