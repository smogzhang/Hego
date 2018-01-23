package cn.ego.service;

import java.util.List;

import cn.ego.bean.EUTreeNode;
import cn.ego.bean.EgoResult;

public interface ContentCatService {

	List<EUTreeNode> listContentCat(long parentId) throws Exception;

	EgoResult createContentCatNode(long parentId, String name);

	EgoResult updateContentCatNode(long id, String name);

	EgoResult deleteContentCatNode(long id);

}
