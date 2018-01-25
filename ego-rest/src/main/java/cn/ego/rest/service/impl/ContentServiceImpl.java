package cn.ego.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ego.mapper.TbContentMapper;
import cn.ego.pojo.TbContent;
import cn.ego.pojo.TbContentExample;
import cn.ego.pojo.TbContentExample.Criteria;
import cn.ego.rest.bean.ADItem;
import cn.ego.rest.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	
	@Override
	public List<ADItem> listAdvertising(long categoryId) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> originalContents = contentMapper.selectByExample(example);
		List<ADItem> adItems = new ArrayList<ADItem>();
		ADItem adItem = null;
		for (TbContent tbContent : originalContents) {
			adItem = new ADItem();
			adItem.setHref(tbContent.getUrl());
			adItem.setAlt(tbContent.getTitleDesc());
			adItem.setSrc(tbContent.getPic());
			adItem.setHeight(240);
			adItem.setWidth(670);
			adItem.setSrcB(tbContent.getPic2());
			adItem.setHeightB(240);
			adItem.setWidthB(550);
			adItems.add(adItem);
		}
		return adItems;
	}

}
