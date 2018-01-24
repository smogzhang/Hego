package cn.ego.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ego.bean.EUDataGridResult;
import cn.ego.bean.EgoResult;
import cn.ego.pojo.TbContent;
import cn.ego.service.ContentService;

@RequestMapping("/content")
@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	/**
	 * 每一次点击内容分类，都会显示相应的内容详情
	 * @param categoryId 内容分类id
	 * @param page 第几页
	 * @param rows 显示多少数量
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EUDataGridResult listContentByPage(long categoryId, int page, int rows) {
		return contentService.listContentByPage(categoryId, page, rows);
	}
	
	/**
	 * 在内容分类下添加一个内容
	 * @param content
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public EgoResult addContent(TbContent content) {
		try {
			contentService.saveContent(content);
		} catch (Exception e) {
			e.printStackTrace();
			return EgoResult.build(1, e.getMessage());
		}
		return EgoResult.ok();
	}
	/**
	 * 根据id删除内容
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public EgoResult deleteContentById(@RequestParam("ids") List<Long> ids) {
		try {
			contentService.removeContentByIds(ids);
		} catch (Exception e) {
			e.printStackTrace();
			return EgoResult.build(1, e.getMessage());
		}
		return EgoResult.ok();
	}
	
	/**
	 * 编辑商品内容
	 * @param content
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public EgoResult editContent(TbContent content) {
		try {
			contentService.updateContent(content);
		} catch (Exception e) {
			e.printStackTrace();
			return EgoResult.build(1, e.getMessage());
		}
		return EgoResult.ok();
	}
	
	
}
