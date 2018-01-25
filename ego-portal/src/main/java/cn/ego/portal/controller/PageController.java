package cn.ego.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ego.portal.service.PorContentService;

@Controller
public class PageController {
	
	@Autowired
	private PorContentService porContentService;
	
	@RequestMapping("/")
	public String indexPage(Model model) {
		System.out.println("PageController.indexPage()");
		model.addAttribute("ad", listContent());
		return "index";
	}
	/**
	 * 展示portal所有内容
	 * @return
	 */
	private String listContent() {
		return porContentService.listADsByContentCategoryId();
	}
}
