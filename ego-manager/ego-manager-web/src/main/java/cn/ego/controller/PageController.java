package cn.ego.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	//打开首页
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}
	
	//展现首页内嵌页面
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page) {
		return page;
	}
	
	//展现首页内嵌内嵌页面
	//商品列表页面的 编辑、删除、上架、下架页面/rest/page/**
	@RequestMapping("/rest/page/{page}")
	public String showRestPage(@PathVariable String page) {
		return page;
	}
	
}
