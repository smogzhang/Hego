package cn.ego.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.ego.bean.PictureResult;
import cn.ego.service.PictureService;

/**
 * 图片上传
 * @author Sully
 *
 */
@Controller
@RequestMapping("/pic")
public class PictureController {

	@Autowired
	private PictureService pictureService;
	
	@RequestMapping("/upload")
	@ResponseBody
	public PictureResult uploadFile(MultipartFile uploadFile) {
		return pictureService.uploadFile(uploadFile);
	}
	
}
