package cn.ego.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ego.bean.EUDataGridResult;
import cn.ego.mapper.TbContentMapper;
import cn.ego.pojo.TbContent;
import cn.ego.pojo.TbContentExample;
import cn.ego.pojo.TbContentExample.Criteria;
import cn.ego.service.ContentService;
import cn.ego.vo.ContentVo;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	
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
	}

	/*删*/
	@Override
	public void removeContentByIds(List<Long> ids) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(ids);
		contentMapper.deleteByExample(example);
	}

	/*改*/
	@Override
	public void updateContent(TbContent content) {
		content.setUpdated(new Date());
		//允许修改content富文本内容
		contentMapper.updateByPrimaryKeyWithBLOBs(content);
	}

}