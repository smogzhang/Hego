package cn.ego.service;

import java.util.List;

import cn.ego.bean.EUTreeNode;

public interface ItemCatService {
	// 查询所有商品分类，用于添加商品
	List<EUTreeNode> listCatItem(long parentId) throws Exception;
}
