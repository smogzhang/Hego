package cn.ego.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.ego.portal.service.PorContentService;
import cn.ego.utils.HttpClientUtil;

@Service
public class PorContentServiceImpl implements PorContentService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${CONTENT_BIG_ADVERTISING}")
	private String CONTENT_BIG_ADVERTISING;
	
	/**
	 * 通过内容分类id查询所有内容，id通过URL传递
	 * @return json数据
	 * [{"srcB":"http://image.ego.com/images/2015/03/03/2015030304360302109345.jpg","height":240,"alt":"","width":670,"src":"http://image.ego.com/images/2015/03/03/2015030304360302109345.jpg","widthB":550,"href":"http://sale.jd.com/act/e0FMkuDhJz35CNt.html?cpdad=1DLSUE","heightB":240}]
	 */
	@Override
	public String listADsByContentCategoryId() {
		return HttpClientUtil.doGet(REST_BASE_URL + CONTENT_BIG_ADVERTISING);
	}

}
