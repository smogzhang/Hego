package cn.ego.service;

import org.springframework.web.multipart.MultipartFile;

import cn.ego.bean.PictureResult;

public interface PictureService {

	PictureResult uploadFile(MultipartFile uploadFile);
	
}
