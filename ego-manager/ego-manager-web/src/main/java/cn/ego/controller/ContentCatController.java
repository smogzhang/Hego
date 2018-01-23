package cn.ego.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ego.bean.EUTreeNode;
import cn.ego.bean.EgoResult;
import cn.ego.service.ContentCatService;

/**
 * 内容分类管理
 * @author Sully
 *
 */
@Controller
@RequestMapping("/content/category")
public class ContentCatController {
	
	@Autowired
	private ContentCatService contentCatService;
	
//	content/category/list
	/**
	 * 查询所有内容分类，以树形结构展示
	 * @param parentId 父节点id
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> queryContentCats(@RequestParam(value="id", defaultValue="0") long parentId) {
		List<EUTreeNode> nodes = null;
		try {
			nodes = contentCatService.listContentCat(parentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodes;
	}
	/**
	 * 新建分类子节点,返回含有主键id的子节点，用于界面渲染
	 * @param parentId 父节点id(当前节点)，用于更新父节点状态以及关联子节点
	 * @param name 子节点名称
	 * @return
	 */
	@RequestMapping("/create")
	@ResponseBody
	public EgoResult createContentCatNode(long parentId, String name) {
		return contentCatService.createContentCatNode(parentId, name);
	}
	/**
	 * 重命名节点
	 * @param id 节点id
	 * @param name 节点新名字
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public EgoResult updateNode(long id, String name) {
		return contentCatService.updateContentCatNode(id, name);
	}
	
	/**
	 * 删除节点,级联删除子节点
	 * @param id 节点id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public EgoResult deleteNode(long id) {
		return contentCatService.deleteContentCatNode(id);
	}
}
