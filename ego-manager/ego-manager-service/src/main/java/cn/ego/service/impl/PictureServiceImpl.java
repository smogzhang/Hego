package cn.ego.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.ego.bean.PictureResult;
import cn.ego.service.PictureService;
import cn.ego.utils.FtpUtil;
import cn.ego.utils.IDUtils;

@Service
public class PictureServiceImpl implements PictureService {

	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	@Value("${FTP_PORT}")
	private String FTP_PORT;
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;//展示图片路径，默认添加了基础路径
	
	
	@Override
	public PictureResult uploadFile(MultipartFile uploadFile) {
		PictureResult result = new PictureResult();
		try {
			String fileName = saveFile(uploadFile);
			if(!"".equals(fileName)) {
				result.setError(0);
				result.setUrl(IMAGE_BASE_URL + fileName);
			} else {
				result.setError(1);
				result.setMessage("上传失败，请重试！！");
				//或者再设置一个 返回出错的图片
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private String saveFile(MultipartFile uploadFile) throws IOException {
		//用于返回新的分配好地址的文件名，即base+newFilePath可以用于回显图片
		String newFilePath = "";
		InputStream inputStream = uploadFile.getInputStream();
		//拼接文件存放路径 基础路径+区别路径(利用天数区别)
		String diffPath = "/" + new SimpleDateFormat("yyyy").format(new Date()) +
							"/" + new SimpleDateFormat("MM").format(new Date()) +
							"/" + new SimpleDateFormat("dd").format(new Date());
		//获取文件原始名称,为了拿到后缀
		String oriFileName = uploadFile.getOriginalFilename();
		String suffix = oriFileName.substring(oriFileName.lastIndexOf("."));
		//重建文件名
		String newFileName = IDUtils.genImageName();
		boolean b = FtpUtil.uploadFile(FTP_ADDRESS, Integer.parseInt(FTP_PORT), FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH, diffPath, newFileName + suffix, inputStream);
		if(b) {
			newFilePath = new StringBuilder().append(diffPath).append("/" + newFileName).append(suffix).toString();
		}
		return newFilePath;
	}

}
