package cn.ego.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ego.bean.EUTreeNode;
import cn.ego.bean.EgoResult;
import cn.ego.mapper.TbContentCategoryMapper;
import cn.ego.pojo.TbContentCategory;
import cn.ego.pojo.TbContentCategoryExample;
import cn.ego.pojo.TbContentCategoryExample.Criteria;
import cn.ego.service.ContentCatService;

@Service
public class ContentCatServiceImpl implements ContentCatService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	/**
	 * 根据id查询，每一次点击发送一次请求
	 */
	@Override
	public List<EUTreeNode> listContentCat(long parentId) throws Exception{
		List<EUTreeNode> nodes = new ArrayList<EUTreeNode>();
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> contentCategories = contentCategoryMapper.selectByExample(example);
		EUTreeNode node = null;
		for (TbContentCategory tbContentCategory : contentCategories) {
			node = new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			if(tbContentCategory.getIsParent()) {
				node.setState("closed");
			} else {
				node.setState("open");
			}
			nodes.add(node);
		}
		return nodes;
	}

	/**
	 * 创建新节点并返回主键id，同步更新父节点
	 * @param parentId 父节点主键id
	 * @param name 新建节点名称
	 */
	@Override
	public EgoResult createContentCatNode(long parentId, String name) {
		Date date = new Date();
		//插入新节点
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setIsParent(false);
		contentCategory.setName(name);
		contentCategory.setParentId(parentId);
		contentCategory.setStatus(1);
		contentCategory.setSortOrder(1);
		contentCategory.setUpdated(date);
		contentCategory.setCreated(date);
		//插入新节点。需要返回主键。(自动映射)
		contentCategoryMapper.insert(contentCategory);
		//如果父节点原先为子节点，则更新父节点
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent()) {
			parent.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		return EgoResult.ok(contentCategory);
	}

	/**
	 * @param id 节点id
	 * @param name 节点新名字
	 * @return
	 */
	@Override
	public EgoResult updateContentCatNode(long id, String name) {
		TbContentCategory oldContentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		oldContentCategory.setUpdated(new Date());
		oldContentCategory.setName(name);
		contentCategoryMapper.updateByPrimaryKey(oldContentCategory);
		return EgoResult.ok(oldContentCategory);
	}

	/**
	 * 递归删除节点
	 * @param id 删除节点id
	 */
	@Override
	public EgoResult deleteContentCatNode(long id) {
		//获取要删除的节点
		TbContentCategory beDeleteNode = contentCategoryMapper.selectByPrimaryKey(id);
		/*递归删除该节点下的所有节点*/
		cascadeDelete(beDeleteNode);
		/*获取该节点的父节点下的所有子节点，若无，则修改状态为子节点*/
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(beDeleteNode.getParentId());
		List<TbContentCategory> childrenNode = contentCategoryMapper.selectByExample(example);
		if(childrenNode != null && childrenNode.size() == 0 ) {
			//没有子节点，修改状态
			TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(beDeleteNode.getParentId());
			parentNode.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKey(parentNode);
		}
		return EgoResult.ok();
	}
	
	private void cascadeDelete(TbContentCategory beDeleteNode) {
		if(beDeleteNode.getIsParent()) {
			//是父节点
			TbContentCategoryExample example = new TbContentCategoryExample();
			Criteria criteria = example.createCriteria();
			criteria.andParentIdEqualTo(beDeleteNode.getId());
			//拿到所有子节点
			List<TbContentCategory> children = contentCategoryMapper.selectByExample(example);
			for (TbContentCategory tbContentCategory : children) {
				cascadeDelete(tbContentCategory);
			}
		}
		//不是父节点，直接删除
		contentCategoryMapper.deleteByPrimaryKey(beDeleteNode.getId());
	}
}
