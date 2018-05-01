package cn.ego.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ego.bean.EUDataGridResult;
import cn.ego.mapper.TbContentMapper;
import cn.ego.pojo.TbContent;
import cn.ego.pojo.TbContentExample;
import cn.ego.pojo.TbContentExample.Criteria;
import cn.ego.service.ContentService;
import cn.ego.utils.HttpClientUtil;
import cn.ego.vo.ContentVo;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${CACHE_DATA_SYNC}")
	private String CACHE_DATA_SYNC;
	@Value("${CONTENT_ADVERTISING_IDENTIFYING}")
	private String CONTENT_ADVERTISING_IDENTIFYING;
	@Value("${ITEM_CATEGORY_IDENTIFYING}")
	private String ITEM_CATEGORY_IDENTIFYING;
	
	/*查*/
	@Override
	public EUDataGridResult listContentByPage(long categoryId, int page, int rows) {
		Date date = new Date();
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		PageHelper.startPage(page, rows);
		List<TbContent> contents = contentMapper.selectByExampleWithBLOBs(example);
		PageInfo<TbContent> info = new PageInfo<>(contents); 
		
		List<ContentVo> vos = new ArrayList<ContentVo>();
		ContentVo vo = null;
		for (TbContent tbContent : contents) {
			vo = new ContentVo();
			vo.setId(tbContent.getId());
			vo.setCategoryId(tbContent.getCategoryId());
			vo.setContent(tbContent.getContent());
			vo.setTitle(tbContent.getTitle());
			vo.setSubTitle(tbContent.getSubTitle());
			vo.setTitleDesc(tbContent.getTitleDesc());
			vo.setUrl(tbContent.getUrl());
			vo.setPic(tbContent.getPic());
			vo.setPic2(tbContent.getPic2());
			vo.setCreated(date);
			vo.setUpdated(date);
			vos.add(vo);
		}
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(vos);
		result.setTotal(info.getTotal());
		return result;
	}

	/*增*/
	@Override
	public void saveContent(TbContent content) throws Exception {
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		contentMapper.insert(content);
		// 调用方法 清除缓存
		syncCacheData(content);
	}

	/*删*/
	@Override
	public void removeContentByIds(List<Long> ids) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(ids);
		contentMapper.deleteByExample(example);
		// 直接删除所有缓存数据，而不是散列表中的某一列
		Map<String, String> param = new HashMap<String, String>();
		param.put("hkey", CONTENT_ADVERTISING_IDENTIFYING);
		param.put("skey", null);
		HttpClientUtil.doGet(REST_BASE_URL + CACHE_DATA_SYNC, param);
	}

	/*改*/
	@Override
	public void updateContent(TbContent content) {
		content.setUpdated(new Date());
		//允许修改content富文本内容
		contentMapper.updateByPrimaryKeyWithBLOBs(content);
		// 调用方法 清除缓存
		syncCacheData(content);
	}
	
	/*删除缓存*/
	private void syncCacheData(TbContent content) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("hkey", CONTENT_ADVERTISING_IDENTIFYING);
		param.put("skey", content.getCategoryId()+"");
		HttpClientUtil.doGet(REST_BASE_URL + CACHE_DATA_SYNC, param);
	}

}