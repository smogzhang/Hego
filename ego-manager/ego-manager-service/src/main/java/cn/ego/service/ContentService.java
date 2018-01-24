package cn.ego.service;

import java.util.List;

import cn.ego.bean.EUDataGridResult;
import cn.ego.pojo.TbContent;

public interface ContentService {

	EUDataGridResult listContentByPage(long categoryId, int page, int rows);

	void saveContent(TbContent content) throws Exception;

	void removeContentByIds(List<Long> ids);

	void updateContent(TbContent content);

}
