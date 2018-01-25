package cn.ego.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.ego.mapper.TbContentMapper;
import cn.ego.pojo.TbContent;
import cn.ego.pojo.TbContentExample;
import cn.ego.pojo.TbContentExample.Criteria;
import cn.ego.rest.bean.ADItem;
import cn.ego.rest.dao.JedisClient;
import cn.ego.rest.service.ContentService;
import cn.ego.utils.JsonUtils;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisCluster;
	@Value("${CONTENT_ADVERTISING_IDENTIFYING}")
	private String CONTENT_ADVERTISING_IDENTIFYING;
	@Override
	public List<ADItem> listAdvertising(long categoryId) {
		//查询缓存
		/**
		 * hkey:大广告位缓存(redis cluster 指定)
		 */
		try {
			String cacheData = jedisCluster.hget(CONTENT_ADVERTISING_IDENTIFYING, categoryId + "");
			if(!StringUtils.isEmpty(cacheData)) {
				return JsonUtils.jsonToList(cacheData, ADItem.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		//若缓存中没有数据，则向数据库查，然后添加缓存
		try {
			if(adItems != null && adItems.size() > 0) {
				jedisCluster.hset(CONTENT_ADVERTISING_IDENTIFYING, categoryId + "", JsonUtils.objectToJson(adItems));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return adItems;
	}

}
