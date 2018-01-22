package cn.ego.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JsonController {

	@RequestMapping("/json")
	@ResponseBody
	public String getJsonData() {
		return "category.json";
	}
	
}
