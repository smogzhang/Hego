package cn.ego.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ego.rest.bean.ADItem;
import cn.ego.rest.service.ContentService;

/**
 * 提供内容查询等服务
 * @author Sully
 *
 */
@Controller
@RequestMapping("/rest")
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	/**
	 * 根据内容id查询内容
	 * @param categoryId
	 * @return
	 */
	@RequestMapping("/content/advertising/{categoryId}")
	@ResponseBody
	public List<ADItem> listADsByContentCategory(@PathVariable long categoryId) {
		return contentService.listAdvertising(categoryId);
	}
	
}
